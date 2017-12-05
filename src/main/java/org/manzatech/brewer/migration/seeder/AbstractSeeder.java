package org.manzatech.brewer.migration.seeder;

import com.github.javafaker.Faker;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class AbstractSeeder<T> {

    protected List<T> data;
    protected SessionFactory sessionFactory;
    protected Faker faker;

    public AbstractSeeder(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
        this.faker = new Faker(new Locale("pt-BR"));
        this.data = new ArrayList<>();
    }

    abstract public void seed();

    public List<T> getData() {
        return data;
    }
}
