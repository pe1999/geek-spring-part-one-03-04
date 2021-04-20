package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // INSERT
        EntityManager em = emFactory.createEntityManager();

//        em.getTransaction().begin();
//
//        User[] users = {
//                new User(null, "beta_user2", "password"),
//                new User(null, "alpha_user3", "password"),
//                new User(null, "new_user4", "password"),
//                new User(null, "arg_user4", "password"),
//                new User(null, "al_user4", "password")
//        };
//        Arrays.stream(users)
//                .forEach(em::persist);
//
//        em.getTransaction().commit();

        // SELECT

//        System.out.println("One user by id");
//        User user = em.find(User.class, 1L);
//        System.out.println(user.getId() + "\t" + user.getUsername());
//
//        System.out.println("All users");
//        List<User> users = em.createQuery("select u from User u", User.class) // HQL, JPQL
//                .getResultList();
//
//        users.forEach(usr -> System.out.println(usr.getId() + "\t" + usr.getUsername()));
//
//        System.out.println("Named query");
//        users = em.createNamedQuery("nameLike", User.class)
//                .setParameter("like", "a%")
//                .getResultList();
//
//        users.forEach(usr -> System.out.println(usr.getId() + "\t" + usr.getUsername()));
//
//        // native SQL query
//        System.out.println("Native SQL query");
//        users = em.createNativeQuery("select * from users", User.class)
//                .getResultList();
//
//        users.forEach(usr -> System.out.println(usr.getId() + "\t" + usr.getUsername()));

        // UPDATE
//        em.getTransaction().begin();

//        User user = em.find(User.class, 1L);
//
//        user.setPassword("1231231231231312321123123");

//        User user = new User(1L, "super_user_1", "12344321");
//
//        em.merge(user);

//        em.getTransaction().commit();

        // DELETE

        em.getTransaction().begin();

//        User user = em.find(User.class, 2L);
//
//        em.remove(user);

//        em.createNamedQuery("remove")
//                .setParameter("id", 3L)
//                .executeUpdate();
//
//        em.getTransaction().commit();

        // SELECT
        User user = em.find(User.class, 4L);
        user = em.find(User.class, 4L);
        user = em.find(User.class, 4L);
        user = em.find(User.class, 4L);
        user = em.find(User.class, 4L);

        em.close();
    }
}
