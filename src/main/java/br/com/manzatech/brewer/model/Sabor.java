package br.com.manzatech.brewer.model;

public enum Sabor {
	ADOCICADA("Adocicada"), AMARGA("Amarga"), FORTE("Forte"), FRUTADA("Frutada"), SUAVE("Suave");
	
	private String nome;
	
	private Sabor(String name) {
		this.nome = name;
	}

	public String getNome() {
		return nome;
	}
	
}
