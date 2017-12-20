package org.manzatech.brewer.migration.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.manzatech.brewer.model.*;
import org.manzatech.brewer.repository.Cidades;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ClientesSeeder extends AbstractSeeder<Cliente> {

    private List<Cidade> cidades;

    public ClientesSeeder(SessionFactory sessionFactory, List<Cidade> cidades) {
        super(sessionFactory);
        this.cidades = cidades;
    }

    @Override
    public void seed() {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        for (int i = 1; i <= 50; i++) {
            final boolean isCompany = ThreadLocalRandom.current().nextBoolean();
            Cliente c = new Cliente();
            c.setTipoPessoa(isCompany ? TipoPessoa.JURIDICA : TipoPessoa.FISICA);
            c.setCpfCnpj(isCompany ? faker.number().digits(14) : faker.number().digits(11));
            c.setEmail(faker.internet().emailAddress());
            c.setNome(isCompany ? faker.company().name() : faker.name().fullName());
            c.setTelefone(faker.phoneNumber().phoneNumber());
            Endereco e = new Endereco();
            e.setCep(faker.regexify("^(\\d{5}-\\d{3})$"));
            e.setCidade(faker.options().nextElement(cidades));
            e.setComplemento(faker.lorem().characters(3, 20));
            e.setLogradouro(faker.address().streetName());
            e.setNumero(faker.number().digits(3));
            c.setEndereco(e);
            this.data.add(c);
            s.save(c);
            if (i % 10 == 0){
                s.flush();
                s.clear();
            }
        }
        s.flush();
        s.clear();
        tx.commit();
        s.close();
    }

}
