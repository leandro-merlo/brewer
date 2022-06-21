package br.com.manzatech.brewer.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.manzatech.brewer.model.Usuario;
import br.com.manzatech.brewer.repositories.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarios.porEmailEAtivo(email);
		Usuario u = usuario.orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas. Tente novamente"));
		return new UsuarioSistema(u, getPermissoes(u));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario u) {
		return new HashSet<SimpleGrantedAuthority>(
			this.usuarios.permissoes(u).
			stream().map((s) -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())
		);
	}

}
