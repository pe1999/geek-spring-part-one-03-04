package ru.geekbrains.dto;

public class ProductFilterDto {

    private String productCostFilterMinStr;

    private String productCostFilterMaxStr;

    private String sortColumn;

    private String sortDirection;

    public ProductFilterDto() {
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

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
