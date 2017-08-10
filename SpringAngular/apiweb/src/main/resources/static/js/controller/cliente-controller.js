appCliente.controller("clienteController", function($scope, $http) {
		$scope.clientes = [];
		$scope.cliente = {};
		
		listarClientes = function(){
			$http({
				method:'GET',
				url:'http://localhost:8080/clientes'
			}).then(function(response) {
				$scope.clientes = response.data;
				console.log(response.data);
				console.log(response.status);
			}, 
			function(response) {
				console.log(response.data);
				console.log(response.status);
			});
		};
		
		$scope.cadastrarCliente = function(){
			$http({
				method:'POST',
				url:'http://localhost:8080/clientes',
				data:$scope.cliente
			}).then(function(response) {
				listarClientes();
				$scope.limparCliente();
				console.log(response.data);
				console.log(response.status);
			}, 
			function(response) {
				console.log(response.data);
				console.log(response.status);
			});
		};
		
		$scope.excluirCliente = function(cliente){
			$http({
				method:'DELETE',
				url:'http://localhost:8080/clientes/' + cliente.id,
			}).then(function(response) {
				$scope.clientes.pop(response.cliente);
				listarClientes();
				console.log(response.data);
				console.log(response.status);
			}, 
			function(response) {
				console.log(response.data);
				console.log(response.status);
			});
		};
		
		$scope.recuperarCliente = function(cliente) {
			$scope.cliente = angular.copy(cliente);
		};
		
		$scope.cancelarAlteracaoCliente = function() {
			$scope.cliente = {};
		};
		
		$scope.limparCliente = function() {
			$scope.cliente = {};
		};
		
		listarClientes();
		
		/*
		$scope.alterarCliente = function(cliente){
			$http({
				method:'PUT',
				url:'http://localhost:8080/clientes/' + cliente.id,
			}).then(function(response) {
				pos = clientes.indexof(cliente);
				$scope.clientes.splice(pos, 1);
			}, 
			function(response) {
				console.log(response.data);
				console.log(response.status);
			});
		};*/
	});