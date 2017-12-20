package org.manzatech.brewer.repository.helpers.cliente;

import org.manzatech.brewer.model.Cliente;
import org.manzatech.brewer.repository.filters.ClienteFilter;
import org.springframework.data.domain.Page;

public interface ClientesQueries {
    public Page<Cliente> filtrar(ClienteFilter cervejaFilter, org.springframework.data.domain.Pageable pageable);
}
