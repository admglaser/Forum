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