package br.com.manzatech.brewer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.manzatech.brewer.model.Cidade;
import br.com.manzatech.brewer.repositories.helper.CidadesQueries;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries {

	public List<Cidade> findByEstadoId(Long estadoId);

	public Optional<Cidade> findByNomeIgnoreCaseAndEstadoId(String nome, Long estadoId);
}
