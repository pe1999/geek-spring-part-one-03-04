package ru.geekbrains.rest;

// http://localhost:8080/spring-mvc-app/swagger-ui/index.html
// http://localhost:8080/spring-mvc-app/v3/api-docs/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.BadRequestException;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductFilterDto;
import ru.geekbrains.service.ProductService;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/filter", produces = "application/json")
    public List<ProductDto> filterProducts(ProductFilterDto productFilterDto) {
        return productService.findWithFilterSpec(productFilterDto);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ProductDto findById(@PathVariable("id") Long id) {
        ProductDto productDto = productService.findById(id)
                .orElseThrow(NotFoundException::new);
        return productDto;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ProductDto create(@RequestBody ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new BadRequestException();
        }
        return productService.save(productDto);
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody ProductDto productDto) {
        if (productDto.getId() == null) {
            throw new BadRequestException();
        }
        productService.save(productDto);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
