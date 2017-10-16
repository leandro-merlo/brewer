package org.manzatech.brewer.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "estilo")
public class Estilo implements Serializable {

    private static final long serialVersionUID = 3324820391581018131L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "estilo")
    private List<Cerveja> cervejas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cerveja> getCervejas() {
        return cervejas;
    }

    public void setCervejas(List<Cerveja> cervejas) {
        this.cervejas = cervejas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estilo estilo = (Estilo) o;

        if (!getId().equals(estilo.getId())) return false;
        return getNome().equals(estilo.getNome());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getNome().hashCode();
        return result;
    }
}
