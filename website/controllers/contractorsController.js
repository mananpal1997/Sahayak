app.controller("contractorsController", function($scope, $http, api, toastr){
	$http.get(api.apiUrl + "Contractors/")
		.then(function(response){
			console.log(response.data);
			$scope.contractors = response.data;
		}, function(error){
			console.log(error.data);
		});
});