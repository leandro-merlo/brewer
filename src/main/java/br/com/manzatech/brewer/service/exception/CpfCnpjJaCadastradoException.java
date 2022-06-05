package br.com.manzatech.brewer.service.exception;

public class CpfCnpjJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CpfCnpjJaCadastradoException() {
		super("CPF ou CNPJ já cadastrdo.");
	}
}
