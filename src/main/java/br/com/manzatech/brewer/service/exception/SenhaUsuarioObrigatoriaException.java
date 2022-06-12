package br.com.manzatech.brewer.service.exception;

public class SenhaUsuarioObrigatoriaException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public SenhaUsuarioObrigatoriaException() {
		super("senha", "A senha é obrigatória");
	}
}
