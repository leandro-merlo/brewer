package org.manzatech.brewer.migration.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.manzatech.brewer.model.Cerveja;
import org.manzatech.brewer.model.Estilo;
import org.manzatech.brewer.model.Origem;
import org.manzatech.brewer.model.Sabor;

import java.math.BigDecimal;
import java.util.List;

public class CervejasSeeder extends AbstractSeeder<Cerveja> {

    private List<Estilo> estilos;

    public CervejasSeeder(SessionFactory sessionFactory, List<Estilo> estilos) {
        super(sessionFactory);
        this.estilos = estilos;
    }

    @Override
    public void seed() {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        for (int i = 1; i <= 50; i++) {
            Cerveja c = new Cerveja();
            c.setNome(faker.beer().name());
            c.setDescricao(faker.lorem().sentence(3, 7));
            c.setEstilo(faker.options().nextElement(estilos));
            c.setOrigem(faker.options().nextElement(Origem.values()));
            c.setComissao(new BigDecimal(faker.number().randomDouble(1, 0, 100)));
            c.setQuantidadeEstoque(faker.number().numberBetween(0, 500));
            c.setSabor(faker.options().nextElement(Sabor.values()));
            c.setTeorAlcoolico(new BigDecimal(faker.number().randomDouble(1, 6, 20)));
            c.setValor(new BigDecimal(faker.number().randomDouble(2, 1, 500)));
            c.setSku(faker.regexify("([a-zA-Z]{2}\\d{4})").toUpperCase());
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
