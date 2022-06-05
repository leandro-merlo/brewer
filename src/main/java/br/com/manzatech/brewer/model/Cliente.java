package br.com.manzatech.brewer.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.validation.ClienteGroupSequenceProvider;
import br.com.manzatech.brewer.model.validation.groups.CnpjGroup;
import br.com.manzatech.brewer.model.validation.groups.CpfGroup;

@Entity
@Table(name = "cliente")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	
	@NotNull(message = "Campo obrigatório")
	@Column(name = "tipo_pessoa")
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@NotBlank(message = "Campo obrigatório")
	@CPF(groups = { CpfGroup.class }, message = "CPF Inválido")
	@CNPJ(groups = { CnpjGroup.class }, message = "CNPJ Inválido")
	private String documento;
	
	private String telefone;
	
	@Email(message = "Email inválido")
	@NotBlank(message = "Campo obrigatório")
	private String email;

	@Embedded
	private Endereco endereco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String getDocumentoSemFormatacao() {
		return this.documento.replaceAll("[^0-9]", "");
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
	
	@PrePersist
	@PreUpdate
	private void OnPersistUpdate() {
		this.documento = this.documento.replaceAll("\\.|-|/", "");
		if (StringUtils.hasText(this.telefone)) {
			String newPhone = this.telefone.replaceAll("[^0-9]", "");
			this.telefone= newPhone;
		}
		if (null != this.endereco && null != this.endereco.getCep()) {
			this.endereco.setCep(this.endereco.getCep().replaceAll("\\.|-|", ""));
		}
	}

}
