app.controller('conversationsController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'conversations/' + pageNumber;
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		debug("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		if (res.data.error) {
			jumpToAbsolutePath("error");
			return;
		}
		$scope.data = res.data;
		
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
		
		$('#conversationSubmit').click(function(){ 
			var subject = $("#subject").val();
			var recipients = $("#recipients").val();
			var text = $("#bbcodeEditor").val();
			
			var postData = {
				"subject": subject,
				"recipients": recipients,
				"text": text,
			};
			$.ajax({ 
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "conversations/new",
				data: JSON.stringify(postData),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){        
					if (data.success) {
						alert("Message sent.")
						$('#newMessageModalForm').modal('toggle');					
						$rootScope.$emit('reload');
						$("#subject").val("");
						$("#recipients").val("");
						$("#bbcodeEditor").val("");
					} else {
						alert(data.message);
					}
				}			
			});
		});
	});
});

