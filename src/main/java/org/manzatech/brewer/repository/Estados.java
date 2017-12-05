package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Estados extends JpaRepository<Estado, Long> {

}
