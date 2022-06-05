package br.com.manzatech.brewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.manzatech.brewer.model.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long>{

}
