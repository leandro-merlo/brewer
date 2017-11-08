package org.manzatech.brewer.migration.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.manzatech.brewer.model.Estilo;

public class EstilosSeeder extends AbstractSeeder<Estilo> {

    public EstilosSeeder(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void seed() {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        for (int i = 0; i < 10; i++) {
            Estilo e = new Estilo();
            e.setNome(faker.beer().style());
            this.data.add(e);
            s.save(e);
        }
        s.flush();
        s.clear();
        tx.commit();
        s.close();
    }
}
