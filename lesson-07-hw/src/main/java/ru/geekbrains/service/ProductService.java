package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductService::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> findById(long id) {
        return productRepository.findById(id)
                .map(ProductService::convertToDto);
    }

    @Transactional
    public void save(ProductDto productDto) {
        productRepository.save(convertToDao(productDto));
    }

    @Transactional
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> findAllByProductCostBetween(BigDecimal min, BigDecimal max) {
        return productRepository.findAllByProductCostBetween(min, max).stream()
                .map(ProductService::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findAllByProductCostLessThan(BigDecimal value) {
        return productRepository.findAllByProductCostLessThanEqual(value).stream()
                .map(ProductService::convertToDto)
                .collect(Collectors.toList());
    };

    public List<ProductDto> findAllByProductCostGreaterThan(BigDecimal value) {
        return productRepository.findAllByProductCostGreaterThanEqual(value).stream()
                .map(ProductService::convertToDto)
                .collect(Collectors.toList());
    };

    private static ProductDto convertToDto(Product product) {
        return new ProductDto(product.getId(), product.getProductname(), product.getProductCost());
    }

    private static Product convertToDao(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getProductname(), productDto.getProductCost());
    }
}
