package org.manzatech.brewer.controller.converter;

import org.manzatech.brewer.model.Estilo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EstilosConverter implements Converter<String, Estilo> {
    @Nullable
    @Override
    public Estilo convert(String source) {
        Estilo estilo = null;
        if (!StringUtils.isEmpty(source)) {
            estilo = new Estilo();
            estilo.setId(Long.parseLong(source));
        }
        return estilo;
    }
}
