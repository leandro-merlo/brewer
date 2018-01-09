package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByEmailIgnoreCase(String email);
}
