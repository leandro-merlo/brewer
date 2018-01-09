package org.manzatech.brewer.service;

import org.manzatech.brewer.model.Usuario;
import org.manzatech.brewer.repository.Usuarios;
import org.manzatech.brewer.service.exception.EmailUsuarioJaCadastradoException;
import org.manzatech.brewer.service.exception.SenhaObrigatoriaParaUsuarioNovoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private Usuarios usuarios;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void salvar(Usuario usuario) throws EmailUsuarioJaCadastradoException, SenhaObrigatoriaParaUsuarioNovoException {
        if (usuarios.findByEmailIgnoreCase(usuario.getEmail()).isPresent()){
            throw new EmailUsuarioJaCadastradoException("O email informado já encontra-se cadastrado! Favor utilizar outro.");
        }
        if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
            throw new SenhaObrigatoriaParaUsuarioNovoException("A senha é obrigatória");
        }

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        usuarios.save(usuario);
    }
}
