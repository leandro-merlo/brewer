package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Cerveja;
import org.manzatech.brewer.repository.helpers.cerveja.CervejasQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>, CervejasQueries{

    public Optional<Cerveja> findBySku(String sku);
}
