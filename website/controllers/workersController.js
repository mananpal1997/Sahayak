app.controller("workersController", function($scope, $http, api, toastr){
	$http.get(api.apiUrl + "Workers/")
		.then(function(response){
			console.log(response.data);
			$scope.workers = response.data;
		}, function(error){
			console.log(error.data);
		});
});