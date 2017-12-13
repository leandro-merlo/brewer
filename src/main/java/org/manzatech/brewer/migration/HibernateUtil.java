package org.manzatech.brewer.migration;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

/**
 * @author imssbora
 */
public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static Connection connection;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                // Create registry builder
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Map<String, String> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "org.mariadb.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mariadb://localhost/brewer?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "1010");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                // Apply settings
                registryBuilder.applySettings(settings);

                // Create registry
                registry = registryBuilder.build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                Class[] classes = PackageScanner.getClasses("org.manzatech.brewer.model");
                for (Class c : classes){
                    sources.addAnnotatedClass(c);
                }

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();


                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

                sessionFactory.
                        getSessionFactoryOptions().getServiceRegistry().
                        getService(ConnectionProvider.class).getConnection();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}