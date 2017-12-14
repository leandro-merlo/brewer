package org.manzatech.brewer.service;

import org.manzatech.brewer.model.Cliente;
import org.manzatech.brewer.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private Clientes repository;


    @Transactional
    public void salvar(Cliente cliente){
        repository.save(cliente);
    }

}
