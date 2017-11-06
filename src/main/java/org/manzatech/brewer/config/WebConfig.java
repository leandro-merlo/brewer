package org.manzatech.brewer.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.manzatech.brewer.controller.CervejasController;
import org.manzatech.brewer.controller.converter.EstilosConverter;
import org.manzatech.brewer.thymeleaf.BrewerDialect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.math.BigDecimal;

@ComponentScan( basePackageClasses = {CervejasController.class})
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine((ISpringTemplateEngine) templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect();
    }

    @Bean
    public BrewerDialect brewerDialect() { return new BrewerDialect(); }

    /**
     * Define qual é o Template Engine a ser utilizado
     * @return
     */
    @Bean
    public TemplateEngine templateEngine(){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        /**
         * Registrando o Layout Dialect para poder extender os Templates
         * numa estrutura hierarquica
         */
        engine.addDialect(layoutDialect());
        engine.addDialect(brewerDialect());
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
        resolver.setCharacterEncoding("UTF-8");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
        	.addResourceLocations("classpath:/static/")
        	.setCachePeriod(3600)
        	.resourceChain(true)
        	.addResolver(new PathResourceResolver());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Bean
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService service = new DefaultFormattingConversionService();
        NumberStyleFormatter bigDecimalConverter = new NumberStyleFormatter("#,##0.00");
        NumberStyleFormatter integerConverter = new NumberStyleFormatter("#,##0");
        service.addConverter(new EstilosConverter());
        service.addFormatterForFieldType(BigDecimal.class, bigDecimalConverter);
        service.addFormatterForFieldType(Integer.class, integerConverter);
        return service;
    }
}
