package org.manzatech.brewer.model.validation;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.manzatech.brewer.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

    @Override
    public List<Class<?>> getValidationGroups(Cliente o) {
        List<Class<?>> grupos = new ArrayList<>();
        grupos.add(Cliente.class);
        if (isClienteSelecionado(o)){
            grupos.add(o.getTipoPessoa().getGrupo());
        }
        return grupos;
    }

    private boolean isClienteSelecionado(Cliente o) {
        return o != null && o.getTipoPessoa() != null;
    }
}
