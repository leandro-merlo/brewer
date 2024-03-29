package br.com.manzatech.brewer.repositories.helper;

import java.util.List;
import java.util.Optional;

import br.com.manzatech.brewer.model.Usuario;

public interface UsuariosQueries extends Queries<Usuario>{
	
	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);

}
