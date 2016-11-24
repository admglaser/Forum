app.controller('settingsController', function($rootScope, $scope, $http) {
	var link = restLink + 'settings/';
	debug("Getting page: " + link);
	$http.get(link, {
		headers : {
			"Authorization" : "Basic " + encoded
		}
	})
	.then(function(res) {
		debug("Result has arrived for " +  link);
		$rootScope.$emit('updateNavbar');		
		if (res.data.error) {
			jumpToAbsolutePath("error");
			return;
		}
		$scope.data = res.data;
		
		$('#uploadSubmit').click(function(){ 	
			var file = $("#fileInput").prop('files')[0];
			console.log(file);
			$.ajax({
				type: "POST",
				contentType: 'multipart/form-data',
				url: restLink + "settings/photo",
				data: file,
				headers: {
					"Authorization": "Basic " + encoded
				},
				processData: false,
				success: function(data){
					if (data.success) {
						$rootScope.$emit('reload');
						alert(data.message);
					} else {
						alert(data.message);
					}
				}
			});
		});
	});
});