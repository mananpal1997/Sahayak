app.controller("servicesController", function($scope, $http, api, toastr){
	$http.get(api.apiUrl + "Services/")
		.then(function(response){
			console.log(response.data);
			$scope.services = response.data;
		}, function(error){
			console.log(error.data);
		});
});