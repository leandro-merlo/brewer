package br.com.manzatech.brewer.service.exception;

public class CidadeJaCadastradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CidadeJaCadastradaException() {
		super("Cidade já cadastrada para este estado");
	}

}
