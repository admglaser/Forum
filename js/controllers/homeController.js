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
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
	});
});