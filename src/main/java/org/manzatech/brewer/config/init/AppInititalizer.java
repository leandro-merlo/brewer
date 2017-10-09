package org.manzatech.brewer.config.init;

import org.manzatech.brewer.config.WebConfig;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInititalizer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Nullable
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * Informa quais são os arquivos de configuração
     * @return
     */
    @Nullable
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ WebConfig.class };
    }

    /**
     * Responsável por informar ao Dispatch Servlet quais os padrões que serão retornados para ele.
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
