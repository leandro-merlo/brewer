package br.com.manzatech.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.manzatech.brewer.service.CadastroCervejaService;
import br.com.manzatech.brewer.storage.FotoStorage;
import br.com.manzatech.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = { CadastroCervejaService.class }, basePackages = "br.com.manzatechh.brewer")
public class ServiceConfig {

	@Bean
	public FotoStorage fotoStorage() {
		return new FotoStorageLocal();
	}
}
