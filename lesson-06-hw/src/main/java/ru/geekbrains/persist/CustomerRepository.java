package ru.geekbrains.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class CustomerRepository {

    @Autowired
    private EntityManagerFactoryContainer emfContainer;

    public Customer findById(Long id) {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();
        Customer customer = em.find(Customer.class, id);
        return customer;
    }

    public List<Customer> findAll() {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();
        List<Customer> customerList = em.createQuery("select c from Customer c",
                Customer.class).getResultList();
        return customerList;
    }

    public void deleteById(Long id) {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();

        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.getTransaction().begin();
            em.remove(customer);
            em.getTransaction().commit();
        }
        em.close();
    }

    public Customer saveOrUpdate(Customer customer) {
        EntityManager em = emfContainer.getEmFactory().createEntityManager();
        em.getTransaction().begin();

        if (customer.getCustomerId() == null) {
            em.persist(customer);
        } else {
            Customer customer1 = findById(customer.getCustomerId());
            if (customer1 != null) {
                customer = new Customer(customer.getCustomerId(), customer.getCustomerName());
                em.merge(customer);
            } else {
                customer = null;
            }
        }
        em.getTransaction().commit();
        em.close();

        return customer;
    }
}
