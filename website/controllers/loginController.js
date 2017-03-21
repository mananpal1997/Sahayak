app.controller("loginController", function($scope, $http, api, toastr, $window){
	
	$scope.login_client = function(){
		var data = {
			"email": $scope.email_client,
			"password": $scope.password_client
		};

		console.log(data);

		$http.post(api.apiUrl + "/Clients/login", data)
			.then(function(response){
				console.log(response.data);
				toastr.success("Successfully login Client");
				$window.localStorage['id'] = response.data.id;
				$window.localStorage['userId'] = response.data.userId;
				$window.localStorage['username'] = response.data.data.username;
				$window.localStorage['type'] = "client";
				$window.location.href = '/'; 
			}, function(error){
				console.log(error.data);
				toastr.error("Invalid Credentials")
			});
	};

	$scope.login_contractor = function(){
		var data = {
			"email": $scope.email_contractor,
			"password": $scope.password_contractor
		};

		console.log(data);

		$http.post(api.apiUrl + "/Contractors/login", data)
			.then(function(response){
				console.log(response.data);
				toastr.success("Successfully login Contractor");
				$window.localStorage['id'] = response.data.id;
				$window.localStorage['userId'] = response.data.userId;
				$window.localStorage['username'] = response.data.data.username;
				$window.localStorage['type'] = "contractor";
				$window.location.href = '/';
			}, function(error){
				console.log(error.data);
				toastr.error("Invalid Credentials")
			});
	};

	$scope.login_worker = function(){
		var data = {
			"email": $scope.email_worker,
			"password": $scope.password_worker
		};

		console.log(data);

		$http.post(api.apiUrl + "/Workers/login", data)
			.then(function(response){
				console.log(response.data);
				toastr.success("Successfully login Worker");
				$window.localStorage['id'] = response.data.id;
				$window.localStorage['userId'] = response.data.userId;
				$window.localStorage['username'] = response.data.data.username;
				$window.localStorage['type'] = "worker";
				$window.location.href = '/';
			}, function(error){
				console.log(error.data);
				toastr.error("Invalid Credentials")
			});
	};

});