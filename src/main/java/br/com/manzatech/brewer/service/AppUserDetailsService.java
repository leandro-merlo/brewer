package br.com.manzatech.brewer.service;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
		return new User(u.getEmail(), u.getSenha(), new HashSet<>());
	}

}
