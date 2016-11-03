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