package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>{
    public List<Cidade> findByEstadoId(Long estadoId);
}
