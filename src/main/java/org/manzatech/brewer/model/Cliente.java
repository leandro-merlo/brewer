package org.manzatech.brewer.repository;

import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.model.TipoPessoa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "tipo_pessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;
    @Column(name = "cpf_cnpj")
    private String CPFCNPJ;

    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;

    private Cidade cidade;

}
