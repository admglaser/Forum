app.controller('navbarController', function($rootScope, $scope, $http) {
	$rootScope.$on('updateNavbar', function(event, data) {
		var link = restLink + "navbar";
		debug("Getting page: " + link);
		$http.get(link, {
			headers : {
				"Authorization" : "Basic " + encoded
			}
		})
		.then(function(res) {
			debug("Result has arrived for " +  link);
			$scope.data = res.data;
			
			$(document).ready(function(){
				var date_input=$('input[name="reg_birthDate"]');
				var options={
					format: "yyyy-mm-dd",
					weekStart: 1,
					todayBtn: true,
					clearBtn: true,
					autoclose: true,
					todayHighlight: true
				};
				date_input.datepicker(options);
			});
			
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
						console.log(data.message);
						if (data.success) {
							console.log("Successfully read notification " + id);
						} else {
							console.log("Failed to read notification " + id + ". " + data.errorMessage);
						}
					}
				});
			};	
		
			$('#loginSubmit').click(function(){ 
				var username = $("#username").val();
				var password = $("#password").val();  
				var enc = btoa(username + ":" + password);
				$.ajax({ 
					type: "GET",
					dataType: "json",
					url: restLink + "login",
					headers: {
						"Authorization": "Basic " + enc
					},
					success: function(data){        
						if (data.success) {
							$rootScope.$emit('reload');
							$('#loginModalForm').modal('toggle');	
							$('#password').val("");
							encoded = enc;											
							
						} else {
							$("#username").val("");
							$("#password").val("");
							alert(data.message);
						}
					}
				});
			});

			$('#registerSubmit').click(function(){
				var postData = {
					username: $('#reg_username').val(),
					displayName: $('#reg_displayname').val(),
					email: $('#reg_email').val(),
					password: $('#reg_password').val(),
					confirmPassword: $('#reg_passwordConfirm').val(),
					birthDate: $('#reg_birthDate').val()
				};
				$.ajax({
					type: "POST",
					dataType: "json",
					contentType: 'application/json',
					url: restLink + "register",
					data: JSON.stringify( postData ),
					headers: {
						"Authorization": "Basic " + encoded
					},
					success: function(data){
						alert(data.message);
						if (data.success) {
							$('#registerModalForm').modal('toggle');
							$('#reg_username').val("");
							$('#reg_displayname').val("");
							$('#reg_email').val("");
							$('#reg_password').val("");
							$('#reg_passwordConfirm').val("");
							$('#reg_birthDate').val("");
						} 
					}
				});
			});
			
			$scope.logout = function() {
				encoded = "";$rootScope.$emit('reload');
			}
			
		});
	});
});