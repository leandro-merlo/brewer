package br.com.manzatech.brewer.repositories.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.repositories.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable);
}
