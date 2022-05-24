package br.com.manzatech.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Estilo;

public class EstiloConverter implements Converter<String, Estilo>{

	@Override
	public Estilo convert(String id) {
		if (!StringUtils.hasText(id)) return null;
		Estilo e = new Estilo();
		e.setId(Long.valueOf(id));
		return e;
	}

}
