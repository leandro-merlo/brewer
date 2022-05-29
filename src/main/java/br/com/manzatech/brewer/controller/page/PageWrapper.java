package br.com.manzatech.brewer.controller.page;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> pagina) {
		this.page = pagina;
		this.uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
	}
	
	public boolean isVazia() {
		return !this.page.hasContent();
	}
	
	public int getAtual() {
		return this.page.getNumber();
	}
	
	public boolean isPrimeira() {
		return this.page.isFirst();
	}
	
	public boolean isUltima() {
		return this.page.isLast();
	}
	
	public int getTotal() {
		return this.page.getTotalPages();
	}
	
	public List<T> getConteudo() {
		return this.page.getContent();
	}
	
	public String uriParaPagina(int pagina) {
		this.uriBuilder = this.uriBuilder.replaceQueryParam("page", pagina);
		return this.uriBuilder.build(true).encode().toUriString();
	}
}
