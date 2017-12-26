package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.model.Estado;
import org.manzatech.brewer.repository.helpers.cidade.CidadesQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries{
    public List<Cidade> findByEstadoId(Long estadoId);
    public Optional<Cidade> findByNomeIgnoreCaseAndEstado(String nome, Estado estado);
}
