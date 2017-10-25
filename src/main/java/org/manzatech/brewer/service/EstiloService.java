package org.manzatech.brewer.service;

import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.repository.Estilos;
import org.manzatech.brewer.service.exception.NomeEstiloJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EstiloService {

    @Autowired
    private Estilos estilos;

    @Transactional
    public Estilo salvar(Estilo estilo) throws NomeEstiloJaCadastradoException {
        if (estilos.findByNomeIgnoreCase(estilo.getNome()).isPresent()){
            throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
        }
        return estilos.saveAndFlush(estilo);
    }
}
