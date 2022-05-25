package br.com.manzatech.brewer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.repositories.Cervejas;
import br.com.manzatech.brewer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));	
	}
}
