package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Estilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long> {

}
