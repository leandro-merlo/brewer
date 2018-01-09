package org.manzatech.brewer.service.exception;

public class SenhaObrigatoriaParaUsuarioNovoException extends RuntimeException {
    public SenhaObrigatoriaParaUsuarioNovoException(String msg) {
        super(msg);
    }
}
