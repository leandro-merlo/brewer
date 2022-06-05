package br.com.manzatech.brewer.model;

import br.com.manzatech.brewer.model.validation.groups.CnpjGroup;
import br.com.manzatech.brewer.model.validation.groups.CpfGroup;

public enum TipoPessoa {

	PESSOA_FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class), 
	PESSOA_JURIDICA("Jurídica", "CNPJ", "009.000.000/0000-00", CnpjGroup.class);
	
	private String nome;
	private String documento;
	private String mascara;
	private Class<?> group;
	
	private TipoPessoa(String nome, String documento, String mascara, Class<?> group) {
		this.nome = nome;
		this.documento = documento;
		this.mascara = mascara;
		this.group = group;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public String getMascara() {
		return mascara;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Class<?> getGroup() {
		return group;
	}
}
