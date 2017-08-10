package com.portifolio.magnum.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portifolio.magnum.entidade.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
