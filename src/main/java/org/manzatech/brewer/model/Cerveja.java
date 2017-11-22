package org.manzatech.brewer.model;

import org.manzatech.brewer.validation.SKU;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cerveja")
public class Cerveja implements Serializable {

    private static final long serialVersionUID = 6101412074514031431L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @SKU()
    @NotBlank(message = "O SKU é obrigatório")
    private String sku;
    @NotBlank(message = "O Nome é obrigatório")
    private String nome;
    @Size(max = 50, min = 1, message = "O tamanho da descrição deve estar entre 1 e 50 caractéres")
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.50")
    @DecimalMax(value = "9999.99")
    private BigDecimal valor;

    @NotNull(message = "O teor alcoólico é obrigatório")
    @DecimalMin(value = "0", message = "O teor alcoólico deve ser maior ou igual a 0")
    @DecimalMax(value = "100.0", message = "O teor alcoólico deve ser menor ou igual a 100")
    @Column(name = "teor_alcoolico")
    private BigDecimal teorAlcoolico;

    @NotNull(message = "A comissão é obrigatória")
    @DecimalMin(value = "0", message = "A comissão deve ser maior ou igual a 0")
    @DecimalMax(value = "100.0", message = "A comissão deve ser menor ou igual a 100")
    private BigDecimal comissao;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Max(value = 9999, message = "A quantidade em estoque dever ser menor ou igual a 9999")
    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

    @NotNull(message = "A origem é obrigatória")
    @Enumerated(value = EnumType.STRING)
    private Origem origem;

    @NotNull(message = "O sabor é obrigatório")
    @Enumerated(value = EnumType.STRING)
    private Sabor sabor;

    @NotNull(message = "O estilo é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_estilo", nullable = false)
    private Estilo estilo;

    @Size(max = 100)
    private String foto;

    @Column(name = "content_type")
    @Size(max = 100)
    private String contentType;

    @PrePersist @PreUpdate
    private void prePersistUpdate(){
        this.sku = this.sku.toUpperCase();
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTeorAlcoolico() {
        return teorAlcoolico;
    }

    public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
    }

    public BigDecimal getComissao() {
        return comissao;
    }

    public void setComissao(BigDecimal comissao) {
        this.comissao = comissao;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public String getFoto() {
        return foto;
    }

    public String getFotoOrMock() {
        return StringUtils.isEmpty(foto) ? "cerveja-mock.png" : foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cerveja cerveja = (Cerveja) o;

        return id != null ? id.equals(cerveja.id) : cerveja.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
