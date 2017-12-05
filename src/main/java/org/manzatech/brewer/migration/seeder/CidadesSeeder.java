package org.manzatech.brewer.migration.seeder;

import com.github.javafaker.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.manzatech.brewer.model.Cidade;
import org.manzatech.brewer.model.Estado;

import javax.swing.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CidadesSeeder extends AbstractSeeder<Cidade>{

    private List<Estado> estados;

    public CidadesSeeder(SessionFactory sessionFactory, List<Estado> estados) {
        super(sessionFactory);
        this.estados = estados;
    }

    @Override
    public void seed() {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        for(Estado estado: estados){
            for (int i = 0; i < 15; i++) {
                Cidade c = new Cidade();
                try {
                    c.setEstado(estado);
                    Address a = faker.address();
                    c.setNome(a.cityName());
                    data.add(c);
                    s.save(c);

                } catch (Exception ex){
                    try {
                        Path p = Files.createFile(FileSystems.getDefault().getPath("/home/leandro/teste.log"));
                        File f = p.toFile();
                        f.createNewFile();
                        PrintWriter pw = new PrintWriter(new FileWriter(f));
                        ex.printStackTrace(pw);
                        pw.flush();
                        pw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ex.printStackTrace(System.err);
                }

            }
            s.flush();
            s.clear();
        }
        s.flush();
        s.clear();
        tx.commit();
        s.close();

    }
}
