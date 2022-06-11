package br.com.manzatech.brewer.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

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
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.github.benmanes.caffeine.cache.Caffeine;

import br.com.manzatech.brewer.controller.CervejasController;
import br.com.manzatech.brewer.controller.converter.CidadeConverter;
import br.com.manzatech.brewer.controller.converter.EstadoConverter;
import br.com.manzatech.brewer.controller.converter.EstiloConverter;
import br.com.manzatech.brewer.thymeleaf.BrewerDialect;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@EnableCaching
@EnableSpringDataWebSupport
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = { CervejasController.class })
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;		
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}
	
	@Bean
	public ISpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);;
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new LayoutDialect());
		engine.addDialect(new BrewerDialect());
		return engine;
	}
	
	private  ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new EstiloConverter());
		conversionService.addConverter(new CidadeConverter());
		conversionService.addConverter(new EstadoConverter());
		
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	
	@Bean
	public CacheManager cacheManager() {
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
				.maximumSize(100)
				.expireAfterAccess(20, TimeUnit.MINUTES);
		CaffeineCacheManager manager = new CaffeineCacheManager();
		manager.setCaffeine(caffeine);
		return manager;
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
}
