package ru.geekbrains.persist;

import java.math.BigDecimal;

public class Product {

    private Long id;

    private String productname;

    private BigDecimal productCost;

    public Product(String productname, BigDecimal productCost) {
        this.productname = productname;
        this.productCost = productCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String username) {
        this.productname = username;
    }

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }
}
