app.controller('messagesController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var conversationNumber = $routeParams.conversationNumber;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'message/' + conversationNumber + '/' + pageNumber;
	console.log("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		console.log("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		if (res.data.error) {
			jumpToAbsolutePath("error");
			return;
		}
		
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.messages.length; i++) {
			var message = $scope.data.messages[i];
			message.text = convertBBCode(message.text, $sce);
		}
		conversationPostParam = conversationNumber;
		
		$(document).ready(function() {
			$("#previewDiv").hide();
		});
		
		$scope.togglePreview = function() {
			var text = $("#previewButton").html();
			if (text == "Preview") {
				$scope.preview();
			} else {
				$scope.edit();
			}
		}
		
		$scope.preview = function() {
			$("#previewButton").html("Edit");
			$("#bbcodeEditor").hide();
			$("#previewDiv").show();
		
			var text = $("#bbcodeEditor").val();
			text = text.replace(/\n/g, "[br][/br]");
			var result = XBBCODE.process({
				text : text,
				removeMisalignedTags : false,
				addInLineBreaks : false
			});
			$("#previewHtml").html(result.html);
		}
		
		$scope.edit = function() {
			$("#previewButton").html("Preview");
			$("#previewDiv").hide();
			$("#bbcodeEditor").show();
		}
		
		$('#messageSubmit').off("click").click(function(){ 
			var text = $("#bbcodeEditor").val();
			var postData = {
				"text": text,
				"conversationNumber": conversationPostParam
			};
			$.ajax({ 
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "message/new",
				data: JSON.stringify(postData),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){        
					if (data.success) {
						alert("Message sent.")
						$('#newReplyModalForm').modal('toggle');					
						$rootScope.$emit('reload');
						$("#bbcodeEditor").val("");
					} else {
						alert("Failed to send message!");
					}
				}			
			});
		});
	});
});
