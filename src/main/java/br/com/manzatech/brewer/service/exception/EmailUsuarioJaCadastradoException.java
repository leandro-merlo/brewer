package br.com.manzatech.brewer.service.exception;

public class EmailUsuarioJaCadastradoException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public EmailUsuarioJaCadastradoException() {
		super("email", "Email já está cadastrado");
	}
}
