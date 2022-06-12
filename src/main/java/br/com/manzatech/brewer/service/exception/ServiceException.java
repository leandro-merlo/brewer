package br.com.manzatech.brewer.service.exception;

public abstract class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	protected String field;
	

	public ServiceException(String field, String message) {
		super(message);
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
