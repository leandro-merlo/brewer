package br.com.manzatech.brewer.model;

public enum Origem {

	NACIONAL("Nacional"), IMPORTADA("Importada");

	private String nome;
	
	private Origem(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
}
