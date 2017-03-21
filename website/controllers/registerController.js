app.controller("registerController", function($scope, api,$http, toastr){

	$scope.location_client = "";
	$scope.location_contractor = "";
	$scope.location_worker = "";


	$scope.current_location = function(){
		if(navigator.geolocation)
		{
			navigator.geolocation.getCurrentPosition(function(position){
				console.log(position.coords.latitude);
				console.log(position.coords.longitude);
				$scope.location_client = position.coords.latitude + ', ' + position.coords.longitude;
				$scope.location_contractor = position.coords.latitude + ', ' + position.coords.longitude;
				$scope.location_worker = position.coords.latitude + ', ' + position.coords.longitude;
				console.log($scope.location_client);
			});
		}
		else
		{
			console.log("Geolocation is not Supported in this browser");
		}

	};
	$scope.register_client = function(){
		var data = {
			"name": $scope.name_client,
			"phone_number": $scope.phone_number_client,
			"location": $scope.location_client,
			"sector": $scope.sector_client,
			"image": "image",
			"username": $scope.username_client,
			"email": $scope.email_client,
			"password": $scope.password_client
		};

		console.log(data);

		$http.post(api.apiUrl + "/Clients", data)
			.then(function(response){
				console.log(response.data);
				toastr.success("Successfully Registered Client");

			}, function(error){
				console.log(error.data);
				toastr.error("Error while Registering. Please try again later")
			});
	};

	$scope.register_contractor = function(){
		var data = {
			"name": $scope.name_contractor,
			"phone_number": $scope.phone_number_contractor,
			"location": $scope.location_contractor,
			"image": "image",
			"username": $scope.username_contractor,
			"email": $scope.email_contractor,
			"password": $scope.password_contractor
		};

		console.log(data);

		$http.post(api.apiUrl + "/Contractors", data)
			.then(function(response){
				console.log(response.data);
				toastr.success("Successfully Registered Contractor");

			}, function(error){
				console.log(error.data);
				toastr.error("Error while Registering. Please try again later")
			});
	};

	$scope.register_worker = function(){
		var data = {
			"name": $scope.name_worker,
			"age": $scope.age_worker,
			"gender": $scope.gender_worker,
			"phone_number": $scope.phone_number_worker,
			"location": $scope.location_worker,
			"image": "image",
			"work_type": $scope.work_type_worker,
			"parent_org": $scope.parent_org_worker,
			"username": $scope.username_worker,
			"email": $scope.email_worker,
			"password": $scope.password_worker
		};

		console.log(data);

		$http.post(api.apiUrl + "/Workers", data)
			.then(function(response){
				console.log(response.data);
				toastr.success("Successfully Registered Worker");

			}, function(error){
				console.log(error.data);
				toastr.error("Error while Registering. Please try again later")
			});
	};

});