package org.manzatech.brewer.service;

import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.repository.Cidades;
import org.manzatech.brewer.service.exception.NomeCidadeJaCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

    @Autowired
    private Cidades repository;

    @Transactional()
    public void salvar(Cidade cidade) {
        if (repository.findByNomeIgnoreCaseAndEstado(cidade.getNome(), cidade.getEstado()).isPresent()){
            throw new NomeCidadeJaCadastradaException("O nome da cidade já está cadastrado para o estado informado.");
        }
        repository.save(cidade);
    }

}
