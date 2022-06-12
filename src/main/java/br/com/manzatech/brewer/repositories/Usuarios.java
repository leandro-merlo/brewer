package br.com.manzatech.brewer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.manzatech.brewer.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);

}
