package ru.geekbrains;

import java.math.BigDecimal;

public class Product {

    private Long id;

    private String productName;

    private BigDecimal productCost;

    public Product(String productName, BigDecimal productCost) {
        this.productName = productName;
        this.productCost = productCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }
}
