package br.com.manzatech.brewer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.manzatech.brewer.model.Cidade;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>{

	public List<Cidade> findByEstadoId(Long estadoId);
}
