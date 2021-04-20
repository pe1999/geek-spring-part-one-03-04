package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Contact;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MainRelations {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // INSERT 1
        EntityManager em = emFactory.createEntityManager();

//        em.getTransaction().begin();
//
//        User user = em.find(User.class, 4L);
//
//        List<Contact> contacts = List.of(
//            new Contact(null, "address", "DreamCity, NiceStreet, 123", user),
//            new Contact(null, "home phone", "1234567", user),
//            new Contact(null, "email", "my_mail@home.com", user)
//        );
//
//        user.getContacts().addAll(contacts);
//
//        em.merge(user);
//
//        em.getTransaction().commit();

        // INSERT 2
//        em.getTransaction().begin();
//
//        User user = em.getReference(User.class, 5L);
//
//        Contact contact = new Contact(null, "address", "MyCity, LovelyStreet, 221", user);
//
//        em.persist(contact);
//
//        em.getTransaction().commit();
//
//        em.refresh(user);

        // SELECT

        //User user = em.find(User.class, 4L);

//        List<User> users = em.createQuery("select distinct u " +
//                "from User u "
//                 + "inner join fetch u.contacts "
//                , User.class)
//                .getResultList();
//
//        for (User usr : users) {
//            List<Contact> contacts = usr.getContacts();
//            System.out.println(contacts);
//        }

        // DELETE

//        em.getTransaction().begin();
//
//        User user = em.find(User.class, 4L);
//        Contact contact = em.getReference(Contact.class, 1L);
//        //user.getContacts().remove(contact);
//
//        em.remove(contact);
//
//        em.getTransaction().commit();

        em.getTransaction().begin();

        em.persist(new Role(null, "GUEST"));
        em.persist(new Role(null, "ADMIN"));
        em.persist(new Role(null, "SUPER_ADMIN"));

        User user = em.find(User.class, 4L);

        user.getRoles().add(em.getReference(Role.class, 1L));
        user.getRoles().add(em.getReference(Role.class, 2L));

        em.merge(user);

        user = em.find(User.class, 5L);

        user.getRoles().add(em.getReference(Role.class, 2L));
        user.getRoles().add(em.getReference(Role.class, 3L));

        em.merge(user);

        em.getTransaction().commit();

        em.close();
    }
}
