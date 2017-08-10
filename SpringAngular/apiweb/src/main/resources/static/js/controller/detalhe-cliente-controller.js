appCliente.controller("detalheClienteController", function($scope, $routeParams, $http) {
	
	$scope.cliente = {};
	
	$http
		.get("clientes/" + $routeParams.clienteId)
				.then(function(response) {
					$scope.cliente = response.data;
				})
				.then(function(response) {
					console.log(response);
				});
	
});