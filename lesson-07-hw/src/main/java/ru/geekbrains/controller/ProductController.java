package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductFilterDto;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;

// http://localhost:8080/hw-spring-mvc-app/user
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productListPage(ProductFilterDto productFilterDto, BindingResult result, Model model) {

        BigDecimal productCostFilterMin;
        BigDecimal productCostFilterMax;

        if (productFilterDto.getProductCostFilterMinStr() == null || productFilterDto.getProductCostFilterMinStr().isBlank()) {
            productCostFilterMin = null;
        } else {
            try {
                productCostFilterMin = new BigDecimal(productFilterDto.getProductCostFilterMinStr().replace(",", "."));
            } catch (Exception e) {
                result.rejectValue("productCostFilterMinStr", "", "Minimum cost shoud be number");
                productCostFilterMin = null;
            }
        }

        if (productFilterDto.getProductCostFilterMaxStr() == null || productFilterDto.getProductCostFilterMaxStr().isBlank()) {
            productCostFilterMax = null;
        } else {
            try {
                productCostFilterMax = new BigDecimal(productFilterDto.getProductCostFilterMaxStr().replace(",", "."));
            } catch (Exception e) {
                result.rejectValue("productCostFilterMaxStr", "", "Maximum cost shoud be number");
                productCostFilterMax = null;
            }
        }

        if (productCostFilterMin == null && productCostFilterMax == null) {
            model.addAttribute("products", productService.findAll());
        }

        if (productCostFilterMin != null && productCostFilterMax != null) {
            model.addAttribute("products", productService.findAllByProductCostBetween(productCostFilterMin, productCostFilterMax));
        }

        if (productCostFilterMin == null && productCostFilterMax != null) {
            model.addAttribute("products", productService.findAllByProductCostLessThan(productCostFilterMax));
        }

        if (productCostFilterMin != null && productCostFilterMax == null) {
            model.addAttribute("products", productService.findAllByProductCostGreaterThan(productCostFilterMin));
        }

        return "product";
    }

    @GetMapping("/{id}")
    public String productForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("productDto", productService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "product_form";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        ProductDto newProductDto = new ProductDto(null, "NewProduct", new BigDecimal(0));
        model.addAttribute("productDto", newProductDto);
        return "product_form";
    }

//    @PostMapping
//    public String submitForm(String id, String productname, String productCost, Model model/*Product product*/) {
//        Product product = new Product(productname, new BigDecimal(0));
//        try {
//            product.setId(Long.valueOf(id));
//        } catch (NumberFormatException e) {
//            product.setId(null);
//        }
//        BigDecimal productCostNumeric;
//        try {
//            productCostNumeric = BigDecimal.valueOf(Double.valueOf(productCost));
//        } catch (NumberFormatException e) {
//            model.addAttribute("product", product);
//            return "product_form";
//        }
//        product.setProductCost(productCostNumeric);
//        productRepository.save(product);
//        return "redirect:/product";
//    }

    @PostMapping
    public String submitForm(@Valid ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "product_form";
        }

        productService.save(productDto);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") long id) {
        productService.delete(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
