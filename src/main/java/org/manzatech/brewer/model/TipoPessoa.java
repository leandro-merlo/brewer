package org.manzatech.brewer.model;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.manzatech.brewer.model.validation.group.CNPJGroup;
import org.manzatech.brewer.model.validation.group.CPFGroup;

public enum TipoPessoa {

    FISICA("Pessoa Física", "CPF", "999.999.999-99", CPFGroup.class){
        @Override
        public String formatarDocumento(String cpfCnpj) {
            return cpfCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
        }
    },
    JURIDICA("Pessoa Jurídica", "CNPJ", "99[9].999.999/9999-99", CNPJGroup.class){
        @Override
        public String formatarDocumento(String cpfCnpj) {
            if (cpfCnpj.length() > 14){
                return cpfCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
            } else {
                return cpfCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
            }
        }
    };

    private String nome;
    private String documento;
    private String mascara;
    private Class<?> grupo;

    TipoPessoa(String nome, String documento, String mascara, Class<?> grupo) {
        this.nome = nome;
        this.documento = documento;
        this.mascara = mascara;
        this.grupo = grupo;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getMascara() {
        return mascara;
    }

    public Class<?> getGrupo() {
        return grupo;
    }

    public static String removerFormatacao(String cpfCnpj){
        return null == cpfCnpj ? null : cpfCnpj.replaceAll("\\.|-|/", "");
    }

    public abstract String formatarDocumento(String cpfCnpj) ;
}
