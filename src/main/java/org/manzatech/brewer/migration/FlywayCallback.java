package org.manzatech.brewer.migration;

import org.flywaydb.core.api.callback.BaseFlywayCallback;
import org.hibernate.SessionFactory;
import org.manzatech.brewer.migration.seeder.CervejasSeeder;
import org.manzatech.brewer.migration.seeder.EstilosSeeder;
import org.manzatech.brewer.repository.Cervejas;

import java.sql.Connection;

public class FlywayCallback extends BaseFlywayCallback {

    public FlywayCallback() {
        super();
    }

    @Override
    public void afterMigrate(Connection connection) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        EstilosSeeder estilosSeeder = new EstilosSeeder(sf);
        estilosSeeder.seed();
        CervejasSeeder cervejasSeeder = new CervejasSeeder(sf, estilosSeeder.getData());
        cervejasSeeder.seed();
    }
}
