app.controller('userOverviewController', function($rootScope, $scope, $http, $routeParams) {
	var userId = $routeParams.userId;
	var link = restLink + 'user/' + userId;
	console.log("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		console.log("Result has arrived for " +  link);
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
	console.log("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		console.log("Result has arrived for " +  link);
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
	console.log("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		console.log("Result has arrived for " +  link);
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
	console.log("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		console.log("Result has arrived for " +  link);	
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
		for (var i = 0; i < $scope.data.posts.length; i++) {
			var post = $scope.data.posts[i];
			post.text = convertBBCode(post.text, $sce);
		}
	});
});
