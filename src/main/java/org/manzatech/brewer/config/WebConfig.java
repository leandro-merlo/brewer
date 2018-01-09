package org.manzatech.brewer.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;
import org.manzatech.brewer.controller.CervejasController;
import org.manzatech.brewer.controller.converter.CidadeConverter;
import org.manzatech.brewer.controller.converter.EstadoConverter;
import org.manzatech.brewer.controller.converter.EstilosConverter;
import org.manzatech.brewer.controller.converter.GrupoConverter;
import org.manzatech.brewer.thymeleaf.BrewerDialect;
import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@ComponentScan( basePackageClasses = {CervejasController.class})
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
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
        DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
        dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault()));
        dateTimeFormatter.registerFormatters(service);

        service.addConverter(new EstilosConverter());
        service.addConverter(new CidadeConverter());
        service.addConverter(new EstadoConverter());
        service.addConverter(new GrupoConverter());
        service.addFormatterForFieldType(BigDecimal.class, bigDecimalConverter);
        service.addFormatterForFieldType(Integer.class, integerConverter);

        return service;
    }

    @Bean
    public CacheManager cacheManager(){
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .initialCapacity(200)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .maximumSize(10000);
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBundleClassLoader(WebConfig.class.getClassLoader());
        ms.addBasenames("classpath:ValidationMessages");
        messageSource.setParentMessageSource(ms);
        return messageSource;
    }

    @Bean
    public Validator validator(MessageSource messageSource){
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Override
    public Validator getValidator() {
        return validator(messageSource());
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new FixedLocaleResolver(Locale.getDefault());
    }
}
