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
	
	
	
	/* .otherwise({
		redirectTo: '/'
	}) */
	;
	
	
});


//route location change event
app.run(function($rootScope, $location, $route) {
	// $rootScope.$on( "$routeChangeStart", function(event, next, current) {
		// $rootScope.$emit('updateNavbar');
	// });
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
		$scope.data = res.data;
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		categoryPostParam = categoryId;
		$rootScope.$emit('updateNavbar');
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

		$scope.data = res.data;
		for (var i = 0; i < $scope.data.posts.length; i++) {
			var post = $scope.data.posts[i];
			post.textBBCode = convertBBCode(post.text, $sce);
		}
		topicPostParam = topicId;
		$rootScope.$emit('updateNavbar');

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
		$scope.data = res.data;
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.topics.length; i++) {
			var topic = $scope.data.topics[i];
			topic.text = convertBBCode(topic.text, $sce);
		}
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.posts.length; i++) {
			var post = $scope.data.posts[i];
			post.text = convertBBCode(post.text, $sce);
		}
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.posts.length; i++) {
			var post = $scope.data.posts[i];
			post.text = convertBBCode(post.text, $sce);
		}
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.messages.length; i++) {
			var message = $scope.data.messages[i];
			message.text = convertBBCode(message.text, $sce);
		}
		conversationPostParam = conversationNumber;
		$rootScope.$emit('updateNavbar');
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
		$scope.data = res.data;
		$rootScope.$emit('updateNavbar');
	});
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
