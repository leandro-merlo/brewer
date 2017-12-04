package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.repository.helpers.cerveja.EstilosQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long>, EstilosQueries {

    public Optional<Estilo> findByNomeIgnoreCase(String nome);
}
