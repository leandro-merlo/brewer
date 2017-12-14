package org.manzatech.brewer.repository.helpers.estilo;

import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.repository.filters.EstiloFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstilosQueries {
    public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable);

}
