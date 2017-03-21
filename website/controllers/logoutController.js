app.controller("logoutController", function($scope, api, $window, toastr){	
	$window.localStorage.clear();
	toastr.success("Logout Successfull");
	$window.location.href = '/';
});