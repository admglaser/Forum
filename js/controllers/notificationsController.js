app.controller('notificationsController', function($scope, $http, $sce) {
	$http.get('notifications.json')
		.then(function(res) {
			$scope.data = res.data;
		}
	);
});