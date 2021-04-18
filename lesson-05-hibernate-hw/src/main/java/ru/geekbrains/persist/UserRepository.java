package ru.geekbrains.persist;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserRepository {
    private final static EntityManagerFactory emFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public User findById(Long id) {
        EntityManager em = emFactory.createEntityManager();
        User user = em.find(User.class, id);
        return user;
    }

    public List<User> findAll() {
        EntityManager em = emFactory.createEntityManager();
        List<User> userList = em.createQuery("select u from User u",
                User.class).getResultList();
        return userList;
    }

    public void deleteById(Long id) {
        EntityManager em = emFactory.createEntityManager();

        User user = em.find(User.class, id);
        if (user != null) {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }
        em.close();
    }

    public User saveOrUpdate(User user) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        if (user.getId() == null) {
            em.persist(user);
        } else {
            User user1 = findById(user.getId());
            if (user1 != null) {
                user = new User(user.getId(), user.getUsername(), user.getPassword());
                em.merge(user);
            } else {
                user = null;
            }
        }
        em.getTransaction().commit();
        em.close();

        return user;
    }
}
