app.controller('membersController', function($rootScope, $scope, $http, $routeParams) {
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'members/' + pageNumber;
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
