app.controller('notificationsController', function($rootScope, $scope, $http, $sce, $routeParams) {
	var pageNumber = 1;
	if ($routeParams.pageNumber) {
		pageNumber = $routeParams.pageNumber;
	}
	var link = restLink + 'notifications/' + pageNumber;
	console.log("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		console.log("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');
		if (res.data.error) {
			jumpToAbsolutePath("error");
			return;
		}
		$scope.data = res.data;
		
		$scope.readNotification = function (id) {
			var postData = {
				"id" : id
			};
			$.ajax({
				type: "POST",
				dataType: "json",
				contentType: 'application/json',
				url: restLink + "notifications/read",
				data: JSON.stringify( postData ),
				headers: {
					"Authorization": "Basic " + encoded
				},
				success: function(data){
					if (data.success) {
						console.log("Successfully read notification " + id);
					} else {
						console.log("Failed to read notification " + id + ". " + data.errorMessage);
					}
				}
			});
		};		
	});
});
