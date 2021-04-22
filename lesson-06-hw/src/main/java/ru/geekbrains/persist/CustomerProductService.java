package ru.geekbrains.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomerProductService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Set<Product> getProductListByCustomerId(Long customerId) {
        return customerRepository.findById(customerId).getProducts();
    }

    public Set<Customer> getCustomerListByProductId(Long productId) {
        return productRepository.findById(productId).getCustomers();
    }

}
