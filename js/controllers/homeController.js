angular.module('forumApp.homeController')
.controller('homeController', function($scope, $http) {
	$http.get('home.json')
	   .then(function(res){
			$scope.data = res.data;
		}
	);
});