package br.com.manzatech.brewer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.manzatech.brewer.model.Cerveja;
import br.com.manzatech.brewer.repositories.helper.CervejasQueries;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>, CervejasQueries {

	public Optional<Cerveja> findBySku(String sku);
}
