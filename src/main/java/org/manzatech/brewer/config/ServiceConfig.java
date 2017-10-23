package org.manzatech.brewer.config;

import org.manzatech.brewer.repository.Estilos;
import org.manzatech.brewer.service.EstiloService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { Estilos.class, EstiloService.class })
public class ServiceConfig {

}
