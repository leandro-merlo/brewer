package br.com.manzatech.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.validation.SKU;

@Entity
@Table(name="cerveja")
public class Cerveja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@SKU
	@NotNull(message = "Campo obrigatório")
	@NotBlank(message = "Campo obrigatório")
	private String sku;
	
	@NotNull(message = "Campo obrigatório")
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	
	@NotNull(message = "Campo obrigatório")
	@Size(min=1, max = 50, message = "Deve ter etre 1 e 50 caractéres")
	private String descricao;
			
	@NotNull(message = "Campo obrigatório")
	@DecimalMin(value = "0.01", message = "Valor mínimo deve ser 0,01")
	@DecimalMax(value = "9999999.99", message = "valor máximo deve ser 9999999,99")
	private BigDecimal valor;
	
	@DecimalMin(value = "0.00", message = "Valor mínimo deve ser 0,00")
	@DecimalMax(value = "100", message = "Valor máximo deve ser 100,00")
	@NotNull(message = "Campo obrigatório")
	@Column(name="teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	@DecimalMin(value = "0.00", message = "Valor mínimo deve ser 0,00")
	@DecimalMax(value = "100", message = "Valor máximo deve ser 100,00")
	@NotNull(message = "Campo obrigatório")
	private BigDecimal comissao;
	
	@Min(value = 0, message = "Valor mínimo deve ser zero")
	@Max(value = 9999, message = "Valor máximo deve ser 9999")
	@NotNull(message = "Campo obrigatório")
	@Column(name="quantidade_estoque")	
	private Integer quantidadeEstoque;

	@NotNull(message = "Campo obrigatório")
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@NotNull(message = "Campo obrigatório")
	@Enumerated(EnumType.STRING)	
	private Sabor sabor;
	
	@NotNull(message = "Campo obrigatório")
	@ManyToOne(optional = false)
	@JoinColumn(name = "estilo_id")
	private Estilo estilo;
	
	@Column
	private String foto;
	
	@Column(name = "content_type")
	private String contentType;
	
	@PrePersist
	@PreUpdate
	protected void onPrePersisAndUpdate() {
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
	
	public String getFotoOuMock() {
		return !StringUtils.hasText(this.foto) ? "mock.png" : this.foto;
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
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
