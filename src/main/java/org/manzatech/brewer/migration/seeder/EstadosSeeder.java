package org.manzatech.brewer.migration.seeder;

import com.github.javafaker.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.manzatech.brewer.model.Estado;

import java.util.ArrayList;

public class EstadosSeeder extends AbstractSeeder<Estado> {

    public EstadosSeeder(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void seed() {
        Session s = sessionFactory.openSession();
        ArrayList<String> nomes = new ArrayList<>();
        Transaction tx = s.beginTransaction();
        for (int i = 0; i < 10; i++) {
            Estado e = new Estado();
            Address a = faker.address();
            String nome;
            while(nomes.contains(nome = a.state())){
                continue;
            }
            nomes.add(nome);
            e.setNome(nome);
            e.setSigla(a.stateAbbr());
            this.data.add(e);
            s.save(e);
        }
        s.flush();
        s.clear();
        tx.commit();
        s.close();
    }
}
