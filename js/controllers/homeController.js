app.controller('homeController', function($rootScope, $scope, $http) {
	var link = restLink + "home";
	console.log("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res){
		console.log("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		$scope.data = res.data;
	});
});
