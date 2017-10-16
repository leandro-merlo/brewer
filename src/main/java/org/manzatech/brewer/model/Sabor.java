package org.manzatech.brewer.model;

public enum Sabor {
    ADOCICADA("Adocicada"),
    AMARGA("Amarga"),
    FORTE("Forte"),
    FRUTADA("frutada"),
    SUAVE("Suave")
    ;

    private String descricao;

    Sabor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
