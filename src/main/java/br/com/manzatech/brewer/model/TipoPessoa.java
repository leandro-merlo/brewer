package br.com.manzatech.brewer.model;

import org.springframework.util.StringUtils;

import br.com.manzatech.brewer.model.validation.groups.CnpjGroup;
import br.com.manzatech.brewer.model.validation.groups.CpfGroup;

public enum TipoPessoa {

	PESSOA_FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class) {
		@Override
		public String formatar(String documento) {
			if (StringUtils.hasText(documento)) {
				return documento.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
			}
			return null;
		}
	}, 
	PESSOA_JURIDICA("Jurídica", "CNPJ", "009.000.000/0000-00", CnpjGroup.class) {
		@Override
		public String formatar(String documento) {
			if (StringUtils.hasLength(documento)) {
				int size = documento.length();
				if (size > 14) {
					return documento.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");					
				} else {
					return documento.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");					
				}
			}
			return null;
		}
	};
	
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
	
	public abstract String formatar(String documento);
	
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
