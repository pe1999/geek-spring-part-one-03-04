package ru.geekbrains.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    @Pattern(regexp = "^[0-9a-zA-Zа-яА-ЯёЁ \\-]{3,}$", message = "Productname length should be more than three symbols")
    @NotBlank(message = "Productname shouldn't be blank")
    private String productname;

    @NotNull
    private BigDecimal productCost;

    public ProductDto() {
    }

    public ProductDto(Long id, String productname, BigDecimal productCost) {
        this.id = id;
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

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }
}
