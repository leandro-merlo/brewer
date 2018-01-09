package org.manzatech.brewer.controller.converter;

import org.manzatech.brewer.model.Grupo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class GrupoConverter implements Converter<String, Grupo>{
    @Override
    public Grupo convert(String source) {
        Grupo grupo = null;
        if (!StringUtils.isEmpty(source)) {
            grupo = new Grupo();
            grupo.setId(Long.parseLong(source));
        }
        return grupo;
    }
}
