package ru.geekbrains.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

@Component
public class ProductRepository {

    @Autowired
    private EntityManagerFactoryContainer emfContainer;

    public Product findById(Long id) {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();
        Product product = em.find(Product.class, id);
        return product;
    }

    public List<Product> findAll() {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();
        List<Product> productList = em.createQuery("select p from Product p",
                Product.class).getResultList();
        return productList;
    }

    public void deleteById(Long id) {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();

        Product product = em.find(Product.class, id);
        if (product != null) {
            em.getTransaction().begin();
            em.remove(product);
            em.getTransaction().commit();
        }
        em.close();
    }

    public Product saveOrUpdate(Product product) {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();
        em.getTransaction().begin();

        if (product.getProductId() == null) {
            em.persist(product);
        } else {
            Product product1 = findById(product.getProductId());
            if (product1 != null) {
                product = new Product(product.getProductId(), product.getProductName(), product.getProductCost());
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
