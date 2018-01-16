package org.manzatech.brewer.repository.helpers.usuarios;

import org.manzatech.brewer.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class UsuariosImpl implements UsuariosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Usuario> porEmailEAtivo(String email) {
        return manager.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }
}
