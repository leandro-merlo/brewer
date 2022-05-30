package br.com.manzatech.brewer.controller.page;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
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
	
	public boolean descendente(String property) {
		if (null != this.page.getSort() && this.page.getSort().getOrderFor(property) != null) {
			return this.page.getSort().getOrderFor(property).isDescending();
		}
		return false;
	}
	
	public boolean ordenadaPor(String property) {
		if (null != this.page.getSort()) {
			return this.page.getSort().getOrderFor(property) != null;
		}
		return false;
	}
	
	public String uriParaPagina(int pagina) {
		this.uriBuilder = this.uriBuilder.replaceQueryParam("page", pagina);
		return this.uriBuilder.build(true).encode().toUriString();
	}
	
	public String urlOrdenada(String property) {
		UriComponentsBuilder newBuilder = UriComponentsBuilder
				.fromUriString(this.uriBuilder.build(true).encode().toUriString());
		String sortValue = String.format("%s,%s", property, this.inverterDirecao(property));
		return newBuilder.replaceQueryParam("sort", sortValue).build(true).encode().toUriString();
	}
	
	private String inverterDirecao(String property) {
		String direcao = "asc";
		
		Order order = this.page.getSort() != null ? page.getSort().getOrderFor(property) : null;
		if (null != order) {
			direcao = order.isAscending() ? "desc" : "asc";
		}
		
		return direcao;
	}
}
