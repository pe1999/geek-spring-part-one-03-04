package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecification {

    public static Specification<Product> productnameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("username"), name);
    }

    public static Specification<Product> minProductCostFilter(BigDecimal minProductCost) {
        return (root, query, builder) -> builder.ge(root.get("productCost"), minProductCost);
    }

    public static Specification<Product> maxProductCostFilter(BigDecimal maxProductCost) {
        return (root, query, builder) -> builder.le(root.get("productCost"), maxProductCost);
    }
}
