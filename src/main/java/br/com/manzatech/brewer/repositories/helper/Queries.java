package br.com.manzatech.brewer.repositories.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.manzatech.brewer.repositories.filter.Filter;

public interface Queries<T> {

	public Page<T> filtrar(Filter filter, Pageable pageable);	
}
