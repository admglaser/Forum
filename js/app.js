var restLink = "http://localhost:8080/ForumApp/rest/";
//var restLink = "http://54.69.96.56:8080/ForumApp/rest/";

var encoded = "";
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
	.when('/topic/:topicId/:pageNumber', {
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
	.when('/user/:userId/topics/:pageNumber', {
		templateUrl : 'pages/user.topics.template.html',
		controller: 'userTopicsController'
	})
	.when('/user/:userId/posts', {
		templateUrl : 'pages/user.posts.template.html',
		controller: 'userPostsController'
	})
	.when('/user/:userId/posts/:pageNumber', {
		templateUrl : 'pages/user.posts.template.html',
		controller: 'userPostsController'
	})
	.when('/user/:userId/likes', {
		templateUrl : 'pages/user.likes.template.html',
		controller: 'userLikesController'
	})
	.when('/user/:userId/likes/:pageNumber', {
		templateUrl : 'pages/user.likes.template.html',
		controller: 'userLikesController'
	})
	
	
	//members
	.when('/members', {
		templateUrl : 'pages/members.template.html',
		controller: 'membersController'
	})
	.when('/members/:pageNumber', {
		templateUrl : 'pages/members.template.html',
		controller: 'membersController'
	})
	
	
	//conversations
	.when('/conversations', {
		templateUrl : 'pages/conversations.template.html',
		controller: 'conversationsController'
	})
	.when('/conversations/:pageNumber', {
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
		/*
		if (data != null) {
			window.scrollX = data.data.scrollX;
			window.scrollY = data.data.scrollY;
		}
		*/
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