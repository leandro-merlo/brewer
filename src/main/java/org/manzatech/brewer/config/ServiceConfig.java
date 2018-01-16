package org.manzatech.brewer.config;

import org.manzatech.brewer.repository.Estilos;
import org.manzatech.brewer.security.AppUserDetailsService;
import org.manzatech.brewer.service.EstiloService;
import org.manzatech.brewer.storage.FotoStorage;
import org.manzatech.brewer.storage.local.FotoStorageLocal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { Estilos.class, EstiloService.class })
public class ServiceConfig {

    @Bean
    public FotoStorage fotoStorage(){
        return new FotoStorageLocal();
    }
}
