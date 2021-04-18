package ru.geekbrains.persist;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProductRepository {
    private final static EntityManagerFactory emFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public Product findById(Long id) {
        EntityManager em = emFactory.createEntityManager();
        Product product = em.find(Product.class, id);
        return product;
    }

    public List<Product> findAll() {
        EntityManager em = emFactory.createEntityManager();
        List<Product> productList = em.createQuery("select p from Product p",
                Product.class).getResultList();
        return productList;
    }

    public void deleteById(Long id) {
        EntityManager em = emFactory.createEntityManager();

        Product product = em.find(Product.class, id);
        if (product != null) {
            em.getTransaction().begin();
            em.remove(product);
            em.getTransaction().commit();
        }
        em.close();
    }

    public Product saveOrUpdate(Product product) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        if (product.getId() == null) {
            em.persist(product);
        } else {
            Product product1 = findById(product.getId());
            if (product1 != null) {
                product = new Product(product.getId(), product.getProductname(), product.getProductCost());
                em.merge(product);
            } else {
                product = null;
            }
        }
        em.getTransaction().commit();
        em.close();

        return product;
    }
}
