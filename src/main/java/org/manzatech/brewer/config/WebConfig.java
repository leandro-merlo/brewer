package org.manzatech.brewer.config;

import org.manzatech.brewer.controller.CervejasController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@ComponentScan( basePackageClasses = {CervejasController.class})
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine((ISpringTemplateEngine) templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    /**
     * Define qual é o Template Engine a ser utilizado
     * @return
     */
    @Bean
    public TemplateEngine templateEngine(){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    /**
     * Informa ao Spring como resolver as questões referentes ao Template Engine
     * @return
     */
    private ITemplateResolver templateResolver(){
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(getApplicationContext());
        resolver.setPrefix("classpath:/templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setSuffix(".html");
        return resolver;
    }

}
