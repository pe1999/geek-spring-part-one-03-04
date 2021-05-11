package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String productListPage(Model model, ProductFilterDto productFilterDto, BindingResult result) {
        if (productFilterDto.getProductCostFilterMinStr() != null && !productFilterDto.getProductCostFilterMinStr().isBlank()) {
            try {
                new BigDecimal(productFilterDto.getProductCostFilterMinStr().replace(",", "."));
            } catch (Exception e) {
                result.rejectValue("productCostFilterMinStr", "", "Minimum cost should be number");
            }
        }

        if (productFilterDto.getProductCostFilterMaxStr() != null && !productFilterDto.getProductCostFilterMaxStr().isBlank()) {
            try {
                new BigDecimal(productFilterDto.getProductCostFilterMaxStr().replace(",", "."));
            } catch (Exception e) {
                result.rejectValue("productCostFilterMaxStr", "", "Maximum cost should be number");
            }
        }

        model.addAttribute("products", productService.findWithFilterSpec(productFilterDto));
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
