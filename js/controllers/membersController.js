app.controller('membersController', function($scope, $http) {
	$http.get('members.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});