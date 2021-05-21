package ru.geekbrains.dto;

import java.math.BigDecimal;

public class CartEntryDto {

    private Long id;

    private Long user_id;

    private String username;

    private Long product_id;

    private String productname;

    private BigDecimal productCost;

    private Long Quantity;

    public CartEntryDto() {
    }

    public CartEntryDto(Long id, Long user_id, String username, Long product_id, String productname, BigDecimal productCost, Long quantity) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.product_id = product_id;
        this.productname = productname;
        this.productCost = productCost;
        Quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    public Long getQuantity() {
        return Quantity;
    }

    public void setQuantity(Long quantity) {
        Quantity = quantity;
    }
}
