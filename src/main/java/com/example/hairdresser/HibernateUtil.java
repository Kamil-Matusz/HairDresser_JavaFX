package com.example.hairdresser;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Representing Hibernate connection for the database
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Generating Hibernate Connection
     * @return sesssion connection
     */
    private static SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            //Create the session factory object.
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
