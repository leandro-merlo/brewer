package org.manzatech.brewer.service;

import org.manzatech.brewer.model.Cerveja;
import org.manzatech.brewer.repository.Cervejas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CervejaService {

    @Autowired
    private Cervejas repository;

    @Transactional
    public void salvar(Cerveja cerveja){
        repository.save(cerveja);
    }
}
