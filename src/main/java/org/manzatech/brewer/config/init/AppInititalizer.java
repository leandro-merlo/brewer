package org.manzatech.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.hibernate.validator.HibernateValidatorConfiguration;
import org.manzatech.brewer.config.PersistenceJPAConfig;
import org.manzatech.brewer.config.SecurityConfig;
import org.manzatech.brewer.config.ServiceConfig;
import org.manzatech.brewer.config.WebConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInititalizer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {
            PersistenceJPAConfig.class,
            ServiceConfig.class,
            SecurityConfig.class,
        };
    }

    /**
     * Informa quais são os arquivos de configuração no contexto Servlet - são carregados após
     * o Root Config Classes
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

    /**
     * Configuração necessária para poder enviar upload de arquivos
     * @param registration
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(""));
    }
}
