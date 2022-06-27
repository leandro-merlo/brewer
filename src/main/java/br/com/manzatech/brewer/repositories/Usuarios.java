package br.com.manzatech.brewer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.manzatech.brewer.model.Usuario;
import br.com.manzatech.brewer.repositories.helper.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByIdIn(Long[] ids);
	
}
