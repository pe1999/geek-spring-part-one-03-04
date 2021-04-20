package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserRepository {

    public final EntityManagerFactory emFactory;

    public UserRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<User> findAll() {
        return executeForEntityManager(
                em -> em.createQuery("select u from User u", User.class)
                        .getResultList()
        );
    }

    public User findById(long id) {
        return executeForEntityManager(
            em -> em.find(User.class, id)
        );
    }

    public void save(User user) {
        executeInTransaction(
                em -> {
                    if (user.getId() == null) {
                        em.persist(user);
                        return;
                    }
                    em.merge(user);
                }
        );
    }

    public void delete(long id) {
        executeInTransaction(
                em -> em.createNamedQuery("remove")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            em.close();
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
