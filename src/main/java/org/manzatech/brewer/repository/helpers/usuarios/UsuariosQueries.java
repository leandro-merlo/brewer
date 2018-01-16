package org.manzatech.brewer.repository.helpers.usuarios;

import org.manzatech.brewer.model.Usuario;

import java.util.Optional;

public interface UsuariosQueries {
   public Optional<Usuario> porEmailEAtivo(String email);
}
