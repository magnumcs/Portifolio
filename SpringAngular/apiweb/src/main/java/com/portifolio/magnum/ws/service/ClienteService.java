package com.portifolio.magnum.ws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portifolio.magnum.entidade.Cliente;
import com.portifolio.magnum.ws.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public Cliente cadastrar(Cliente cliente){
		clienteRepository.save(cliente);
		return cliente;
	}
	
	public Cliente buscarPorId(Integer id){
		return clienteRepository.findOne(id);
	}
	
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	}
	
	public Cliente editar(Cliente cliente){
		clienteRepository.save(cliente);
		return cliente;
	}
	
	public void remover(Cliente cliente){
		clienteRepository.delete(cliente);
	}

}
