var app = angular.module('myApp', ['ui.router', 'toastr', 'ngMap']);

app.constant("api", {
	apiUrl : "http://52.66.168.240:3000/api/"
})


app.config(function($stateProvider, $locationProvider, $urlRouterProvider){
	$stateProvider
		.state("home", {
			url : "/",
			templateUrl : "views/home.html",
			controller : "homeController"
		})
		.state("register", {
			url : "/register",
			templateUrl : "views/register.html",
			controller : "registerController"
		})
		.state("login", {
			url : "/login",
			templateUrl : "views/login.html",
			controller : "loginController"
		})
		.state("workers", {
			url : "/workers",
			templateUrl : "views/workers.html",
			controller : "workersController"
		})
		.state("services", {
			url : "/services",
			templateUrl : "views/services.html",
			controller : "servicesController"
		})
		.state("contractors", {
			url : "/contractors",
			templateUrl : "views/contractors.html",
			controller : "contractorsController"
		})
		.state("logout", {
			url : "/logout",
			controller : "logoutController"
		})
		.state("map", {
			url : "/map",
			templateUrl : "views/map.html",
			controller : "mapController"
		})
		.state("admin", {
			url : "/admin",
			templateUrl : "views/admin.html",
			controller : "adminController"
		});

	$urlRouterProvider.otherwise("/");
    $locationProvider.html5Mode(true);
});