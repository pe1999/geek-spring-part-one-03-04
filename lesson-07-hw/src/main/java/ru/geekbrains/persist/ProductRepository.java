package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductCostBetween(BigDecimal min, BigDecimal max);

    List<Product> findAllByProductCostLessThanEqual(BigDecimal value);

    List<Product> findAllByProductCostGreaterThanEqual(BigDecimal value);

}
