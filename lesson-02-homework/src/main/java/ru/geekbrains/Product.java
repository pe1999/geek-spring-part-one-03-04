package ru.geekbrains;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class Product {

    private Long id;

    private String productName;

    private String productDescription;

    private BigDecimal productCost;

    public Product(String productName, String productDescription, BigDecimal productCost) {
        this.productName = productName;
        this.productDescription = productDescription;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }
}
