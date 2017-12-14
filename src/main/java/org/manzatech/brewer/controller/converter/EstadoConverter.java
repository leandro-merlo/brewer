package org.manzatech.brewer.controller.converter;

import org.manzatech.brewer.model.Estado;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class EstadoConverter implements Converter<String, Estado>{

    @Nullable
    @Override
    public Estado convert(String s) {
        Estado estado = null;
        if (!StringUtils.isEmpty(s)) {
            estado = new Estado();
            estado.setId(Long.parseLong(s));
        }
        return estado;
    }
}
