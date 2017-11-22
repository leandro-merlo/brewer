package org.manzatech.brewer.repository.helpers.cerveja;

import org.manzatech.brewer.model.Cerveja;
import org.manzatech.brewer.repository.filters.CervejaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CervejasQueries {

    public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable);
}
