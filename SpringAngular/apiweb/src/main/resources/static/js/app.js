var appCliente = angular.module("appCliente", ['ngRoute']);

appCliente.config(function($routeProvider, $locationProvider) {
	
	$routeProvider
		.when('/clientes', {
			templateUrl:'view/cliente.html',
			controller:'clienteController'})
		.when('/clientes/:clienteId', {
			templateUrl:'view/detalhe_cliente.html',
			controller:'detalheClienteController'})
		.when('/cidades', {
			templateUrl:'view/cidade.html',
			controller:'cidadeController'})
		.when('/estados', {
			templateUrl:'view/estado.html',
			controller:'estadoController'})
		.when('/login', {
			templateUrl:'view/login.html',
			controller:'loginController'})
		.otherwise({redirectTo:'/'});
	
		$locationProvider.html5Mode(true);
});

appCliente.controller("mainController", function($scope, $routeParams, $location, $route) {
	
	$scope.$location = $location;
	$scope.$route = $route;
	$scope.$routeParams = $routeParams;
	
});