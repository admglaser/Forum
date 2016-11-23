var restLink = "http://localhost:8080/ForumApp/rest/";
//var restLink = "http://54.69.96.56:8080/ForumApp/rest/";

//var username = null; //robertk
//var password = null; //bbb
var encoded = ""; //btoa(username + ":" + password);
var conversationPostParam = null;
var topicPostParam = null;
var categoryPostParam = null;

var app = angular.module('forumApp', [ 'ngRoute', 'ui.bootstrap' ]);


//navigation
app.config(function($routeProvider) {
	$routeProvider
	
	//home
	.when('/', {
		templateUrl : 'pages/home.template.html',
		controller: 'homeController'
	})
	
	
	//subcategory
	.when('/category/:subcategoryId', {
		templateUrl : 'pages/subcategory.template.html',
		controller: 'subcategoryController'
	})
	.when('/category/:subcategoryId/:pageNumber', {
		templateUrl : 'pages/subcategory.template.html',
		controller: 'subcategoryController'
	})
	
	
	//topic
	.when('/topic/:topicId', {
		templateUrl : 'pages/topic.template.html',
		controller: 'topicController'
	})
	.when('/topic/:topicId/:postId', {
		templateUrl : 'pages/topic.template.html',
		controller: 'topicController'
	})
	
	
	//user
	.when('/user/:userId', {
		templateUrl : 'pages/user.overview.template.html',
		controller: 'userOverviewController'
	})
	.when('/user/:userId/topics', {
		templateUrl : 'pages/user.topics.template.html',
		controller: 'userTopicsController'
	})
	.when('/user/:userId/posts', {
		templateUrl : 'pages/user.posts.template.html',
		controller: 'userPostsController'
	})
	.when('/user/:userId/likes', {
		templateUrl : 'pages/user.likes.template.html',
		controller: 'userLikesController'
	})
	
	
	//members
	.when('/members', {
		templateUrl : 'pages/members.template.html',
		controller: 'membersController'
	})
	
	
	//conversations
	.when('/conversations', {
		templateUrl : 'pages/conversations.template.html',
		controller: 'conversationsController'
	})
	
	
	//messages
	.when('/message/:conversationNumber', {
		templateUrl : 'pages/messages.template.html',
		controller: 'messagesController'
	})
	.when('/message/:conversationNumber/:pageNumber', {
		templateUrl : 'pages/messages.template.html',
		controller: 'messagesController'
	})
	
	
	//notifications
	.when('/notifications', {
		templateUrl : 'pages/notifications.template.html',
		controller: 'notificationsController'
	})
	.when('/notifications/:pageNumber', {
		templateUrl : 'pages/notifications.template.html',
		controller: 'notificationsController'
	})

	
	//settings
	.when('/settings', {
		templateUrl : 'pages/settings.template.html',
		controller: 'settingsController'
	})
	
	
	//errorMessage
	.when('/error', {
		templateUrl : 'pages/error.template.html',
		controller: 'errorController'
	})
	.otherwise({
		redirectTo: '/error',
		controller: 'errorController'
	}) 
	
	
	;
});


//route location change event
app.run(function($rootScope, $location, $route) {
	$rootScope.$on('reload', function(event, data) {
		$route.reload();
	});

	$rootScope.$setLastQuote = function(post) {
		$rootScope.$lastQuote = post;
		if (post == null) {
			$('#bbcodeEditor').val(null);
		}
	};

	$rootScope.$getLastQuote = function() {
		return $rootScope.$lastQuote;
	};
});


//navbar
app.controller('navbarController', function($rootScope, $scope, $http) {
	$rootScope.$on('updateNavbar', function(event, data) {
		var link = restLink + "navbar";
		debug("Getting page: " + link);
		$http.get(link, {
			headers : {
				"Authorization" : "Basic " + encoded
			}
		})
		.then(function(res) {
			debug("Result has arrived for " +  link);
			$scope.data = res.data;
			
			
			$(document).ready(function(){
				var date_input=$('input[name="reg_birthDate"]');
				var options={
					format: "yyyy-mm-dd",
					weekStart: 1,
					todayBtn: true,
					clearBtn: true,
					autoclose: true,
					todayHighlight: true
				};
				date_input.datepicker(options);
			});
			
			$scope.readNotification = function (id) {
				var postData = {
					"id" : id
				};
				$.ajax({
					type: "POST",
					dataType: "json",
					contentType: 'application/json',
					url: restLink + "notifications/read",
					data: JSON.stringify( postData ),
					headers: {
						"Authorization": "Basic " + encoded
					},
					success: function(data){
						if (data.success) {
							console.log("Successfully read notification " + id);
						} else {
							console.log("Failed to read notification " + id + ". " + data.errorMessage);
						}
					}
				});
			};	
		
			$('#loginSubmit').click(function(){ 
				var username = $("#username").val();
				var password = $("#password").val();  
				var enc = btoa(username + ":" + password);
				$.ajax({ 
					type: "GET",
					dataType: "json",
					url: restLink + "login",
					headers: {
						"Authorization": "Basic " + enc
					},
					success: function(data){        
						if (data.success) {
							encoded = enc;
							$('#loginModalForm').modal('toggle');					
							$rootScope.$emit('reload');
							$('#password').val("");
						} else {
							$("#username").val("");
							$("#password").val("");
							alert("Invalid username or password!");
						}
					}
				});
			});

			$scope.register = function() {
				var postData = {
					username: $('#reg_username').val(),
					displayName: $('#reg_displayname').val(),
					email: $('#reg_email').val(),
					password: $('#reg_password').val(),
					confirmPassword: $('#reg_passwordConfirm').val(),
					birthDate: $('#reg_birthDate').val()
				};
				$.ajax({
					type: "POST",
					dataType: "json",
					contentType: 'application/json',
					url: restLink + "register",
					data: JSON.stringify( postData ),
					headers: {
						"Authorization": "Basic " + encoded
					},
					success: function(data){
						if (data.success) {
							alert("Successfully registered.");
							$('#registerModalForm').modal('toggle');
							$('#reg_username').val("");
							$('#reg_displayname').val("");
							$('#reg_email').val("");
							$('#reg_password').val("");
							$('#reg_passwordConfirm').val("");
							$('#reg_birthDate').val("");
						} else {
							alert("Registration failed:\n\n" + data.errorMessage);
						}
					}
				});
			}
			
			$scope.logout = function() {
				encoded = "";$rootScope.$emit('reload');
			}
			
		});
	});
});


//home
app.controller('homeController', function($rootScope, $scope, $http) {
	var link = restLink + "home";
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res){
		debug("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
	});
});


//subcategory
app.controller('subcategoryController', function($rootScope, $scope, $http, $routeParams) {
	var categoryId = $routeParams.subcategoryId;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'category/' + categoryId + "/" + pageNumber;
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
					if (data.success) {
						$('#startNewTopicModalForm').modal('toggle');
						jumpToAbsolutePath("topic/" + data.topic);
						alert("Topic has been created.");
					} else {
						alert("Failed to create topic:\n\n" + data.errorMessage);
					}
				}
			});
		}

		$scope.toggleFollowingCategory = function() {
			var btnText = $("#followCategoryButton").html();
			if (btnText == "Follow") {
				$scope.followCategory();
			} else {
				$scope.unfollowCategory();
			}
		}

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
					if (data.success) {
						$("#followCategoryButton").html("Unfollow");
						alert("You are now following this category.");
					} else {
						alert("Failed to follow category:\n\n" + data.errorMessage);
					}
				}
			});
		}

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
					if (data.success) {
						$("#followCategoryButton").html("Follow");
						alert("You have stopped following this category.");
					} else {
						alert("Failed to stop following category:\n\n" + data.errorMessage);
					}
				}
			});
		}
	
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
	});
});


//topic
app.controller('topicController', function($rootScope, $scope, $http, $routeParams, $sce) {
	var topicId = $routeParams.topicId;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'topic/' + topicId + "/" + pageNumber;
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
				post.style = post.style + style.style + ";";
			}
		}

		if ($scope.data.isFollowedByMember == true) {
			$('#followTopicButton').html("Unfollow");
		}

		$scope.openQuotePanel = function (username, userLink, postNumber, postLink, text) {
			var linkToUser = createBBCodeLink(userLink, username);
			var linkToQuote = createBBCodeLink(postLink, "posted");
			var quote = surroundWithBBCodeTag("I", surroundWithBBCodeTag("B", linkToUser) + " has " + linkToQuote + ":") + "\n" + surroundWithBBCodeTag("QUOTE", text) + "\n";
			$('#bbcodeEditor').val(quote);
			edit();
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
					if (data.success) {
						$rootScope.$emit('reload');
						alert("You have liked this topic.");
					} else {
						alert("Failed to like post:\n\n" + data.errorMessage);
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
					if (data.success) {
						$rootScope.$emit('reload');
						alert("You do no longer like this post.");
					} else {
						alert("Failed to stop the like of post:\n\n" + data.errorMessage);
					}
				}
			});
		}
	});
});


//members
app.controller('membersController', function($rootScope, $scope, $http, $routeParams) {
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'members/' + pageNumber;
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		debug("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
	});
});


//user
app.controller('userOverviewController', function($rootScope, $scope, $http, $routeParams) {
	var userId = $routeParams.userId;
	var link = restLink + 'user/' + userId;
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		debug("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
	});
});
app.controller('userTopicsController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var userId = $routeParams.userId;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'user/' + userId + '/topics/' + pageNumber;
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		debug("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.topics.length; i++) {
			var topic = $scope.data.topics[i];
			topic.text = convertBBCode(topic.text, $sce);
		}
	});
});
app.controller('userPostsController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var userId = $routeParams.userId;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'user/' + userId + '/posts/' + pageNumber;
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		debug("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.posts.length; i++) {
			var post = $scope.data.posts[i];
			post.text = convertBBCode(post.text, $sce);
		}
	});
});
app.controller('userLikesController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var userId = $routeParams.userId;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'user/' + userId + '/likes/' + pageNumber;
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		debug("Result has arrived for " +  link);	
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.posts.length; i++) {
			var post = $scope.data.posts[i];
			post.text = convertBBCode(post.text, $sce);
		}
	});
});


//conversations
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


//messages
app.controller('messagesController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var conversationNumber = $routeParams.conversationNumber;
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'message/' + conversationNumber + '/' + pageNumber;
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
		
		$('#messageSubmit').click(function(){ 
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


//notifications
app.controller('notificationsController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'notifications/' + pageNumber;
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
		
		$scope.readNotification = function (id) {
			var postData = {
				"id" : id
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "notifications/read",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					if (data.success) {
						console.log("Successfully read notification " + id);
					} else {
						console.log("Failed to read notification " + id + ". " + data.errorMessage);
					}
				}
			});
		};		
	});
});


//settings
app.controller('settingsController', function($rootScope, $scope, $http) {
	var link = restLink + 'settings/';
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
		
		$('#uploadSubmit').click(function(){ 	
			var file = $("#fileInput").prop('files')[0];
			console.log(file);
			$.ajax({
				type: "POST",
				contentType: 'multipart/form-data',
				url: restLink + "settings/photo",
				data: file,
				headers: {
					"Authorization": "Basic " + encoded
				},
				processData: false,
				success: function(data){
					if (data.success) {
						$rootScope.$emit('reload');
						alert("Image uploaded.");
					} else {
						alert(data.errorMessage);
					}
				}
			});
		});
	});
});

app.controller('errorController', function($rootScope) {
	$rootScope.$emit('updateNavbar');		
});

function createAbsolutePath(absolutePath) {
	var currentURL = window.location.href;

	var indexOfHashtag = currentURL.indexOf("#");
	var rootURL = currentURL.substr(0, indexOfHashtag + 1);

	return rootURL + "/" + absolutePath;
}

function jumpToAbsolutePath(absolutePath) {
	window.location.href = createAbsolutePath(absolutePath);
}

function createBBCodeLink(link, textToGetSurrounded) {
	return "[inner_link=" + link + "]" + textToGetSurrounded + "[/inner_link]";
}

function surroundWithBBCodeTag(tagToSurroundWith, textToGetSurrounded) {
	return "[" + tagToSurroundWith + "]" + textToGetSurrounded + "[/" + tagToSurroundWith + "]";
}

function convertBBCode(text, $sce) {
	var result = XBBCODE.process({
		text : text,
		removeMisalignedTags : false,
		addInLineBreaks : false
	});
	return $sce.trustAsHtml(result.html);
}

function debug(text) {
	console.log(text);
}