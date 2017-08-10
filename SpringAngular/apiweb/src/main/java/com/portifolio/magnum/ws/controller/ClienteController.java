package com.portifolio.magnum.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.portifolio.magnum.entidade.Cliente;
import com.portifolio.magnum.ws.service.ClienteService;

@RestController
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	List<Cliente> listaClientes = new ArrayList<>();
	
	@RequestMapping(method=RequestMethod.POST, value="/clientes", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente){
		Cliente clienteSalvo = clienteService.cadastrar(cliente);
		System.out.println("Cadastrou!");
		return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/clientes", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cliente>> listarClientes(){
		listaClientes = clienteService.listar();
		System.out.println("Lista!");
		return new ResponseEntity<List<Cliente>>(listaClientes, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/clientes/{id}")
	public ResponseEntity<Cliente> removerCliente(@PathVariable Integer id){
		Cliente clienteEncontrado = clienteService.buscarPorId(id);
		clienteService.remover(clienteEncontrado);
		System.out.println("Removeu!");
		return new ResponseEntity<Cliente>(HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cliente> buscarPorIdCliente(@PathVariable Integer id){
		Cliente clienteEncontrado = clienteService.buscarPorId(id);
		if(clienteEncontrado == null){
			System.out.println("Não encontrou!");
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}
		System.out.println("Achou cliente!");
		return new ResponseEntity<Cliente>(clienteEncontrado,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/clientes", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cliente> alterarCliente(@RequestBody Cliente cliente){
		Cliente clienteAlterado = clienteService.editar(cliente);
		System.out.println("Alterou!");
		return new ResponseEntity<>(clienteAlterado, HttpStatus.UPGRADE_REQUIRED);
	}

}
