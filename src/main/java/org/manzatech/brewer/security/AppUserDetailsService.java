package org.manzatech.brewer.security;

import org.manzatech.brewer.model.Usuario;
import org.manzatech.brewer.repository.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private Usuarios usuarios;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarios.findByEmailIgnoreCase(username);
        Usuario u = user.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos"));
        return new User(u.getEmail(), u.getSenha(), new HashSet<>());
    }
}
