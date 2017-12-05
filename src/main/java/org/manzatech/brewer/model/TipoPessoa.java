package org.manzatech.brewer.model;

public enum TipoPessoa {

    FISICA("Pessoa Física", "CPF", "999.999.999-99"),
    JURIDICA("Pessoa Jurídica", "CNPJ", "99[9].999.999/9999-99");

    private String nome;
    private String documento;
    private String mascara;

    TipoPessoa(String nome, String documento, String mascara) {
        this.nome = nome;
        this.documento = documento;
        this.mascara = mascara;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getMascara() {
        return mascara;
    }
}
