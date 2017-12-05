package org.manzatech.brewer.migration;

import org.flywaydb.core.api.callback.BaseFlywayCallback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.manzatech.brewer.migration.seeder.CervejasSeeder;
import org.manzatech.brewer.migration.seeder.CidadesSeeder;
import org.manzatech.brewer.migration.seeder.EstadosSeeder;
import org.manzatech.brewer.migration.seeder.EstilosSeeder;
import org.manzatech.brewer.model.Estado;
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
        EstadosSeeder estadosSeeder = new EstadosSeeder(sf);
        estadosSeeder.seed();
        CidadesSeeder cidadesSeeder = new CidadesSeeder(sf, estadosSeeder.getData());
        cidadesSeeder.seed();
    }

    public static void main(String[] args){
        HibernateUtil.getSessionFactory();
        FlywayCallback fc = new FlywayCallback();
        fc.afterMigrate(HibernateUtil.getConnection());
        System.exit(0);
    }
}
