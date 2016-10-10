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
		/* .otherwise({
			redirectTo: '/'
		}) */
	;
});

app.controller('homeController', function($scope, $http) {
	$http.get('http://54.69.96.56:8080/ForumApp/rest/categories_summary')
	   .then(function(res){
			$scope.data = res.data;
		}
	);
});

app.controller('categoryController', function($scope, $http, $routeParams) {
	$categoryId = $routeParams.categoryId;
	$http.get('category' + $categoryId + '.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});

app.controller('topicController', function($scope, $http, $routeParams) {
	$topicId = $routeParams.topicId;
	$postId = $routeParams.postId;
	$http.get('topic.json')
		.then(function(res) {
			$scope.data = res.data;
			for (var i = 0; i < $scope.data.posts.length; i++) {
				var post = $scope.data.posts[i];
				var result = XBBCODE.process({
					text : post.text,
					removeMisalignedTags : false,
					addInLineBreaks : false
				});
				post.text = result.html;
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
