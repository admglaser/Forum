app.controller('categoryController', function($scope, $http, $routeParams) {
	$categoryId = $routeParams.categoryId;
	$http.get('category.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});