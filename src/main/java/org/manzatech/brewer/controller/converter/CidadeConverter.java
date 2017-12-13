package org.manzatech.brewer.controller.converter;

import org.manzatech.brewer.model.Cidade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class CidadeConverter implements Converter<String, Cidade>{

    @Nullable
    @Override
    public Cidade convert(String s) {
        Cidade cidade = null;
        if (!StringUtils.isEmpty(s)) {
            cidade = new Cidade();
            cidade.setId(Long.parseLong(s));
        }
        return cidade;
    }
}
