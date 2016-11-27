app.controller('topicController', function($rootScope, $scope, $http, $routeParams, $sce) {
	var topicId = $routeParams.topicId;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'topic/' + topicId + "/" + pageNumber;
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

		$(document).ready(function() {
			$("#previewDiv").hide();
		});
		
		$scope.data = res.data;	
		topicPostParam = topicId;
		
		if ($scope.data.canFollow) {
			$('#followTopicButton').prop('disabled', false);
		} else {
			$('#followTopicButton').prop('disabled', true);
		}

		if ($scope.data.canReply) {
			$('#newReplyButton').prop('disabled', false);
		} else {
			$('#newReplyButton').prop('disabled', true);
		}

		for (var i = 0; i < $scope.data.posts.length; i++) {
			var post = $scope.data.posts[i];
			post.textBBCode = convertBBCode(post.text, $sce);

			if (post.isPostLiked) {
				post.likePostButtonText = "Unlike";
			} else {
				post.likePostButtonText = "Like";
			}

			post.isQuoteDisabled = ! $scope.data.canReply;
			post.isLikeDisabled = ! $scope.data.canReply;

			for (var j = 0; j < post.styles.length; j++) {
				var style = post.styles[j];
				if (post.style == null) {
					post.style = "";
				}
				post.style = post.style + style + ";";
			}
		}

		if ($scope.data.isFollowedByMember == true) {
			$('#followTopicButton').html("Unfollow");
		}

		$scope.submitPost = function () {
			var postText = $('#bbcodeEditor').val();
			var lastQuote = angular.element(document.body).scope().$root.$getLastQuote();
			// Delete the quoted text from the beginning of the post.
			//   Not supported that the user deletes from the quote.
			if (lastQuote != null && lastQuote.text != null) {
				postText = postText.slice(lastQuote.text.length);
			}
			postText = postText.replace(/\n/g, "[br][/br]");

			var postData = {
				"topic" : topicPostParam,
				"text" : postText
			};

			if (lastQuote != null) {
				postData.quote = lastQuote.text;
				postData.quotePostNumber = lastQuote.postNumber;
			}

			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "topic/new",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$('#newPostModalForm').modal('toggle');
						$rootScope.$emit('reload', { data: { scrollX : window.scrollX, scrollY : window.scrollY } });
					}
				}
			});
		};


		$scope.toggleFollowingTopic = function () {
			var btnText = $("#followTopicButton").html();
			if (btnText == "Follow") {
				$scope.followTopic();
			} else {
				$scope.unfollowTopic();
			}
		};

		$scope.followTopic = function () {
			var postData = {
				topic: topicPostParam,
				isFollowRequest: true
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "topic/follow",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$("#followTopicButton").html("Unfollow");
					}
				}
			});
		};

		$scope.unfollowTopic = function() {
			var postData = {
				topic: topicPostParam,
				isFollowRequest: false
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "topic/follow",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$("#followTopicButton").html("Follow");
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
		};

		$scope.openQuotePanel = function(username, userLink, postNumber, postLink, text) {
			var linkToUser = createBBCodeLink(userLink, username);
			var linkToQuote = createBBCodeLink(postLink, "posted");
			var quote = surroundWithBBCodeTag("I", surroundWithBBCodeTag("B", linkToUser) + " has " + linkToQuote + ":") + "\n" + surroundWithBBCodeTag("QUOTE", text) + "\n";
			$('#bbcodeEditor').val(quote);
			$scope.edit();
			$('#newPostModalForm').modal('toggle');

			var quoteToSave = { text: quote, postNumber: postNumber };
			$scope.setLastQuote(quoteToSave);
		};

		$scope.setLastQuote = function(quoteToSave) {
			$rootScope.$setLastQuote(quoteToSave);
		};

		$scope.toggleLikingPost = function (post) {
			//var btnText = $("#likePostButton").text();
			if (post.isPostLiked == false) {
				$scope.likePost(post);
			} else {
				$scope.unlikePost(post);
			}
		};

		$scope.likePost = function (post) {
			var postData = {
				topic: topicPostParam,
				postNumber: post.postNumber,
				isLikeRequest: true
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "topic/like",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$rootScope.$emit('reload');
					} 
				}
			});
		};

		$scope.unlikePost = function (post) {
			var postData = {
				topic: topicPostParam,
				postNumber: post.postNumber,
				isLikeRequest: false
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "topic/like",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					alert(data.message);
					if (data.success) {
						$rootScope.$emit('reload');
					} 
				}
			});
		}
	});
});
