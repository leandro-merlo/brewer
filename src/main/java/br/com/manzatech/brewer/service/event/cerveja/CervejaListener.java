package br.com.manzatech.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.manzatech.brewer.storage.FotoStorage;

@Component
public class CervejaListener {

	@Autowired
	private FotoStorage storage;
	
	@EventListener(condition = "#event.temFoto()")
	public void cervejaSalva(CervejaSalvaEvent event) {
		this.storage.salvar(event.getCerveja().getFoto());
		
	}
}
