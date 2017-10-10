package org.manzatech.brewer.config.init;

import org.manzatech.brewer.config.WebConfig;
import org.springframework.lang.Nullable;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import sun.awt.CharsetString;
import sun.misc.CharacterEncoder;

import javax.servlet.Filter;
import java.nio.charset.Charset;

public class AppInititalizer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * Informa quais são os arquivos de configuração
     * @return
     */
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

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return new Filter[]{
            encodingFilter,
        };
    }
}
