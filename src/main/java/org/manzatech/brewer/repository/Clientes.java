package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Cliente;
import org.manzatech.brewer.repository.helpers.cliente.ClientesQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {
    public Optional<Cliente> findByCpfCnpj(String cpfcnpj);
}
