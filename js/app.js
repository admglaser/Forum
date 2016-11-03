var app = angular.module('forumApp', 
		[ 
			'ngRoute', 
			'ui.bootstrap',
			'forumApp.homeController'
		]
);

app.config(function($routeProvider) {
	$routeProvider
		
		//home
		.when('/', {
			templateUrl : 'pages/home.template.html',
			controller: 'homeController'
		})
		
		
		//subcategory
		.when('/category/:categoryId', {
			templateUrl : 'pages/category.template.html',
			controller: 'categoryController'
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
		.when('/messages/:conversationId', {
			templateUrl : 'pages/messages.template.html',
			controller: 'messagesController'
		})
		
		
		//notifications
		.when('/notifications', {
			templateUrl : 'pages/notifications.template.html',
			controller: 'notificationsController'
		})
		.when('/notifications/:notificationId', {
			templateUrl : 'pages/notifications.template.html',
			controller: 'notificationsController'
		})
		
		
		/* .otherwise({
			redirectTo: '/'
		}) */
	;
});

app.controller('navbarController', function($scope, $http) {
	$http.get('navbar.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});

function convertBBCode(text, $sce) {
	var result = XBBCODE.process({
		text : text,
		removeMisalignedTags : false,
		addInLineBreaks : false
	});
	return $sce.trustAsHtml(result.html);
}