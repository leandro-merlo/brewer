package br.com.manzatech.brewer.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import br.com.manzatech.brewer.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente>{

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		List<Class<?>> groups = new ArrayList<Class<?>>();
		groups.add(Cliente.class);
		if (isTipoSelecionado(cliente)) {
			groups.add(cliente.getTipoPessoa().getGroup());
		}
		return groups;
	}

	private boolean isTipoSelecionado(Cliente cliente) {
		return null != cliente && null != cliente.getTipoPessoa();
	}

}
