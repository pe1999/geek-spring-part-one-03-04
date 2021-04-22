package ru.geekbrains.persist;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name ="CUSTOMER_NAME", unique = true, nullable = false)
    private String customerName;

    @ManyToMany
    @JoinTable(
            name = "CUSTOMERS_PRODUCTS",
            joinColumns = @JoinColumn(name = "CUSTOMER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private Set<Product> products;

    public Customer() {
    }

    public Customer(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
