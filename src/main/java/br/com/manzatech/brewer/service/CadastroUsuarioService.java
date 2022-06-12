package br.com.manzatech.brewer.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Usuario;
import br.com.manzatech.brewer.repositories.Usuarios;
import br.com.manzatech.brewer.service.exception.EmailUsuarioJaCadastradoException;
import br.com.manzatech.brewer.service.exception.SenhaUsuarioObrigatoriaException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void salvar(Usuario usuario) throws EmailUsuarioJaCadastradoException, SenhaUsuarioObrigatoriaException {
		Optional<Usuario> optional = this.usuarios.findByEmail(usuario.getEmail());
		if (optional.isPresent()) {
			throw new EmailUsuarioJaCadastradoException();
		}
		if (usuario.isNovo() && !StringUtils.hasText(usuario.getSenha())) {
			throw new SenhaUsuarioObrigatoriaException();
		}
		if (usuario.isNovo()) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmarSenha(usuario.getSenha());
		}
		this.usuarios.save(usuario);
	}
}
