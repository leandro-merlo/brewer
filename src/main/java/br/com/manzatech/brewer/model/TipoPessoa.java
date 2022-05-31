package br.com.manzatech.brewer.model;

public enum TipoPessoa {

	PESSOA_FISICA("Física", "CPF", "000.000.000-00"), 
	PESSOA_JURIDICA("Jurídica", "CNPJ", "009.000.000/0000-00");
	
	private String nome;
	private String documento;
	private String mascara;
	
	private TipoPessoa(String nome, String documento, String mascara) {
		this.nome = nome;
		this.documento = documento;
		this.mascara = mascara;
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
}
