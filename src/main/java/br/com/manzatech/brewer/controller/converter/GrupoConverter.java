package br.com.manzatech.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo>{

	@Override
	public Grupo convert(String id) {
		if (!StringUtils.hasText(id)) return null;
		Grupo g = new Grupo();
		g.setId(Long.valueOf(id));
		return g;
	}

}
