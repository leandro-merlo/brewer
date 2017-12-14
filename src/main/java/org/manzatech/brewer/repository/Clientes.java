package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Cliente;
import org.manzatech.brewer.repository.helpers.cliente.ClientesQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {
}
