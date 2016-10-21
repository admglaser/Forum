var app = angular.module('forumApp', [ 'ngRoute', 'ui.bootstrap' ]);

app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'pages/home.template.html',
			controller: 'homeController'
		})
		.when('/category/:categoryId', {
			templateUrl : 'pages/category.template.html',
			controller: 'categoryController'
		})
		.when('/topic/:topicId', {
			templateUrl : 'pages/topic.template.html',
			controller: 'topicController'
		})
		.when('/topic/:topicId/:postId', {
			templateUrl : 'pages/topic.template.html',
			controller: 'topicController'
		})
		.when('/members', {
			templateUrl : 'pages/members.template.html',
			controller: 'membersController'
		})
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
		.when('/conversations', {
			templateUrl : 'pages/conversations.template.html',
			controller: 'conversationsController'
		})
		.when('/messages/:conversationId', {
			templateUrl : 'pages/messages.template.html',
			controller: 'messagesController'
		})
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

app.controller('homeController', function($scope, $http) {
	$http.get('home.json')
	   .then(function(res){
			$scope.data = res.data;
		}
	);
});

app.controller('categoryController', function($scope, $http, $routeParams) {
	$categoryId = $routeParams.categoryId;
	$http.get('category.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});

app.controller('topicController', function($scope, $http, $routeParams, $sce, $location, $anchorScroll) {
	$topicId = $routeParams.topicId;
	$postId = $routeParams.postId;
	$http.get('topic.json')
		.then(function(res) {
			$scope.data = res.data;
			for (var i = 0; i < $scope.data.posts.length; i++) {
				var post = $scope.data.posts[i];
				post.text = convertBBCode(post.text, $sce);
			}
		}
	);
});

app.controller('navbarController', function($scope, $http) {
	$http.get('navbar.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});

app.controller('membersController', function($scope, $http) {
	$http.get('members.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});

app.controller('userOverviewController', function($scope, $http) {
	$http.get('user.overview.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});

app.controller('userTopicsController', function($scope, $http, $sce) {
	$http.get('user.topics.json')
		.then(function(res) {
			$scope.data = res.data;
			for (var i = 0; i < $scope.data.topics.length; i++) {
				var topic = $scope.data.topics[i];
				topic.text = convertBBCode(topic.text, $sce);
			}
		}
	);
});

app.controller('userPostsController', function($scope, $http, $sce) {
	$http.get('user.posts.json')
		.then(function(res) {
			$scope.data = res.data;
			for (var i = 0; i < $scope.data.posts.length; i++) {
				var post = $scope.data.posts[i];
				post.text = convertBBCode(post.text, $sce);
			}
		}
	);
});

app.controller('userLikesController', function($scope, $http, $sce) {
	$http.get('user.likes.json')
		.then(function(res) {
			$scope.data = res.data;
			for (var i = 0; i < $scope.data.posts.length; i++) {
				var post = $scope.data.posts[i];
				post.text = convertBBCode(post.text, $sce);
			}
		}
	);
});

app.controller('conversationsController', function($scope, $http, $sce) {
	$http.get('conversations.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});

app.controller('messagesController', function($scope, $http, $sce) {
	$http.get('messages.json')
		.then(function(res) {
			$scope.data = res.data;
			for (var i = 0; i < $scope.data.messages.length; i++) {
				var message = $scope.data.messages[i];
				message.text = convertBBCode(message.text, $sce);
			}
		}
	);
});

app.controller('notificationsController', function($scope, $http, $sce) {
	$http.get('notifications.json')
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