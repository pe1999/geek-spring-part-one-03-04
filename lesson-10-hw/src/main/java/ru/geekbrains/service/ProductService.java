package ru.geekbrains.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductFilterDto;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;

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
    public ProductDto save(ProductDto productDto) {
        Product savedProduct = productRepository.save(convertToDao(productDto));
        productDto.setId(savedProduct.getId());
        return productDto;
    }

    @Transactional
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> findWithFilterSpec(ProductFilterDto productFilterDto) {
        BigDecimal productCostFilterMin;
        BigDecimal productCostFilterMax;

        if (productFilterDto.getProductCostFilterMinStr() == null ||
                productFilterDto.getProductCostFilterMinStr().isBlank()) {
            productCostFilterMin = null;
        } else {
            try {
                productCostFilterMin = new BigDecimal(productFilterDto.getProductCostFilterMinStr()
                        .replace(",", "."));
            } catch (Exception e) {
                productCostFilterMin = null;
            }
        }

        if (productFilterDto.getProductCostFilterMaxStr() == null ||
                productFilterDto.getProductCostFilterMaxStr().isBlank()) {
            productCostFilterMax = null;
        } else {
            try {
                productCostFilterMax = new BigDecimal(productFilterDto.getProductCostFilterMaxStr()
                        .replace(",", "."));
            } catch (Exception e) {
                productCostFilterMax = null;
            }
        }

        Specification<Product> spec = Specification.where(null);
        if (productCostFilterMin != null) {
            spec = spec.and(ProductSpecification.minProductCostFilter(productCostFilterMin));
        }
        if (productCostFilterMax != null) {
            spec = spec.and(ProductSpecification.maxProductCostFilter(productCostFilterMax));
        }

        String sortColumn = (productFilterDto.getSortColumn() == null || productFilterDto.getSortColumn() == "" ||
                productFilterDto.getSortDirection() == null || productFilterDto.getSortDirection().isBlank()) ?
                "id" : productFilterDto.getSortColumn();

        Sort.Order sortOrder = ((productFilterDto.getSortDirection() != null &&
                !productFilterDto.getSortDirection().equals("desc")) ||
                productFilterDto.getSortDirection() == null || productFilterDto.getSortDirection().isBlank()) ?
                Sort.Order.asc(sortColumn) : Sort.Order.desc(sortColumn);

        return productRepository.findAll(spec, Sort.by(sortOrder)).stream()
                .map(ProductService::convertToDto)
                .collect(Collectors.toList());
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
