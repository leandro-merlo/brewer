package org.manzatech.brewer.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Cerveja {

    @NotBlank(message = "O SKU é obrigatório")
    private String sku;
    @NotBlank(message = "O Nome é obrigatório")
    private String nome;
    @Size(max = 50, min = 1, message = "O tamanho da descrição deve estar entre 1 e 50 caractéres")
    private String descricao;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
