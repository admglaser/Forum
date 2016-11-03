app.controller('conversationsController', function($scope, $http, $sce) {
	$http.get('conversations.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});