package org.manzatech.brewer.service;

import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.repository.Estilos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EstiloService {

    @Autowired
    private Estilos estilos;

    public void salvar(Estilo estilo){
        estilos.save(estilo);
    }
}
