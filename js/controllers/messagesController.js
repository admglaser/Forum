app.controller('messagesController', function($scope, $http, $sce) {
	$http.get('messages.json')
		.then(function(res) {
			$scope.data = res.data;
			for (var i = 0; i < $scope.data.messages.length; i++) {
				var message = $scope.data.messages[i];
				message.text = convertBBCode(message.text, $sce);
			}
		}
	);
});