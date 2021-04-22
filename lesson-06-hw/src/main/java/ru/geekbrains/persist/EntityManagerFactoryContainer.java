package ru.geekbrains.persist;

import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class EntityManagerFactoryContainer {

    private final static EntityManagerFactory emFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public EntityManagerFactory getEmFactory() {
        return emFactory;
    }

}
