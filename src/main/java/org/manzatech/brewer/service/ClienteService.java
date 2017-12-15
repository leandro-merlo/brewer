package org.manzatech.brewer.service;

import org.manzatech.brewer.model.Cliente;
import org.manzatech.brewer.model.TipoPessoa;
import org.manzatech.brewer.repository.Clientes;
import org.manzatech.brewer.service.exception.DocumentoClienteJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private Clientes repository;


    @Transactional
    public void salvar(Cliente cliente) throws DocumentoClienteJaCadastradoException {
        Optional<Cliente> clienteExistente = repository.findByCpfCnpj(cliente.getCpfCnpjSemFormatacao());
        if (clienteExistente.isPresent()){
            String msg = cliente.getTipoPessoa() == TipoPessoa.FISICA ?
                    "CPF já cadastrado" : "CNPJ já cadastrado";
            throw new DocumentoClienteJaCadastradoException(msg);
        }
        repository.save(cliente);
    }

}
