package br.com.manzatech.brewer.repositories.helper;

import java.util.List;

import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.repositories.filter.CervejaFilter;

public interface CervejasQueries {

	public List<Cerveja> filtrar(CervejaFilter cervejaFilter);
}
