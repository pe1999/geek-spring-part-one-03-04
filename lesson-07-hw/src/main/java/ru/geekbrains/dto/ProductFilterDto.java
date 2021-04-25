package ru.geekbrains.dto;

public class ProductFilterDto {

    private String productCostFilterMinStr;

    private String productCostFilterMaxStr;

    public ProductFilterDto() {
    }

    public ProductFilterDto(String productCostFilterMinStr, String productCostFilterMaxStr) {
        this.productCostFilterMinStr = productCostFilterMinStr;
        this.productCostFilterMaxStr = productCostFilterMaxStr;
    }

    public String getProductCostFilterMinStr() {
        return productCostFilterMinStr;
    }

    public void setProductCostFilterMinStr(String productCostFilterMinStr) {
        this.productCostFilterMinStr = productCostFilterMinStr;
    }

    public String getProductCostFilterMaxStr() {
        return productCostFilterMaxStr;
    }

    public void setProductCostFilterMaxStr(String productCostFilterMaxStr) {
        this.productCostFilterMaxStr = productCostFilterMaxStr;
    }
}
