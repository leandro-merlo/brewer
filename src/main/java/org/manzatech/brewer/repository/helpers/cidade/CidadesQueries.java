package org.manzatech.brewer.repository.helpers.cidade;

import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.repository.filters.CidadeFilter;
import org.springframework.data.domain.Page;

public interface CidadesQueries {
    public Page<Cidade> filtrar(CidadeFilter cidadeFilter, org.springframework.data.domain.Pageable pageable);
}
