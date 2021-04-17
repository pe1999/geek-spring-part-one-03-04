package ru.geekbrains.persist;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class Product {

    private Long id;

    @Size(min = 3, message = "Productname should be longer than 3")
    @NotBlank(message = "Productname shouldn't be blank")
    private String productname;

    @NotNull(message = "Cost shouldn't be blank")
    @Digits(message = "Cost shouldn't have more than two digits after the dot", integer = 100, fraction = 2)
    @PositiveOrZero(message = "Cost should be greater than or equal to zero")
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
