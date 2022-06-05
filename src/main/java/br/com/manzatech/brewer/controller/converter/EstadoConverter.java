package br.com.manzatech.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Estado;

public class EstadoConverter implements Converter<String, Estado>{

	@Override
	public Estado convert(String id) {
		if (!StringUtils.hasText(id)) return null;
		Estado e = new Estado();
		e.setId(Long.valueOf(id));
		return e;
	}

}
