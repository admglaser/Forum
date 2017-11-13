app.controller('subcategoryController', function($rootScope, $scope, $http, $routeParams) {
	var categoryId = $routeParams.subcategoryId;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'category/' + categoryId + "/" + pageNumber;
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
		categoryPostParam = categoryId;
		
		if ($scope.data.canFollow) {
			$('#followCategoryButton').prop('disabled', false);
		} else {
			$('#followCategoryButton').prop('disabled', true);
		}

		if ($scope.data.canStartTopic) {
			$('#startNewTopicButton').prop('disabled', false);
		} else {
			$('#startNewTopicButton').prop('disabled', true);
		}

		if ($scope.data.isFollowedByMember == true) {
			$("#followCategoryButton").html("Unfollow");
		}
		
		
		$(document).ready(function() {
			$("#previewDiv").hide();
		});

		$scope.submitTopic = function() {
			var postText = $('#bbcodeEditor').val();
			postText = postText.replace(/\n/g, "[br][/br]");
			var title = $('#title').val();

			var postData = {
				"category" : categoryPostParam,
				"title" : title,
				"text" : postText
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "category/new",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$('#startNewTopicModalForm').modal('toggle');
						jumpToAbsolutePath("topic/" + data.topic);
					}
				}
			});
		};

		$scope.toggleFollowingCategory = function() {
			var btnText = $("#followCategoryButton").html();
			if (btnText == "Follow") {
				$scope.followCategory();
			} else {
				$scope.unfollowCategory();
			}
		};

		$scope.followCategory = function() {
			var postData = {
				category: categoryPostParam,
				isFollowRequest: true
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "category/follow",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$("#followCategoryButton").html("Unfollow");
					} 
				}
			});
		};

		$scope.unfollowCategory = function() {
			var postData = {
				category: categoryPostParam,
				isFollowRequest: false
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "category/follow",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$("#followCategoryButton").html("Follow");
					} 
				}
			});
		};
	
		$scope.togglePreview = function() {
			var text = $("#previewButton").html();
			if (text == "Preview") {
				$scope.preview();
			} else {
				$scope.edit();
			}
		};

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
		};

		$scope.edit = function() {
			$("#previewButton").html("Preview");
			$("#previewDiv").hide();
			$("#bbcodeEditor").show();
		}
	});
});
