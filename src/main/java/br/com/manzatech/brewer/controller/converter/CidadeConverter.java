package br.com.manzatech.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade>{

	@Override
	public Cidade convert(String id) {
		if (!StringUtils.hasText(id)) return null;
		Cidade c = new Cidade();
		c.setId(Long.valueOf(id));
		return c;
	}

}
