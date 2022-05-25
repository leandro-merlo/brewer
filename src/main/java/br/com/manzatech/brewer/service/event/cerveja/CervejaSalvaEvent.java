package br.com.manzatech.brewer.service.event.cerveja;

import br.com.manzatech.brewer.model.Cerveja;

public class CervejaSalvaEvent {
	
	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		super();
		this.cerveja = cerveja;
	}
	
	public Cerveja getCerveja() {
		return cerveja;
	}
	
	public boolean temFoto() {
		return !this.getCerveja().getFoto().isBlank();
	}

}
