package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import java.math.BigDecimal;

// http://localhost:8080/hw-spring-mvc-app/user
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String productListPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product";
    }

    @GetMapping("/{id}")
    public String userForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productRepository.findById(id));
        return "product_form";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        Product newProduct = new Product("NewProduct", new BigDecimal(0));
        model.addAttribute("product", newProduct);
        return "product_form";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") long id) {
        productRepository.delete(id);
        return "redirect:/product";
    }

    @PostMapping
    public String submitForm(String id, String productname, String productCost, Model model/*Product product*/) {
        Product product = new Product(productname, new BigDecimal(0));
        try {
            product.setId(Long.valueOf(id));
        } catch (NumberFormatException e) {
            product.setId(null);
        }
        BigDecimal productCostNumeric;
        try {
            productCostNumeric = BigDecimal.valueOf(Double.valueOf(productCost));
        } catch (NumberFormatException e) {
            model.addAttribute("product", product);
            return "product_form";
        }
        product.setProductCost(productCostNumeric);
        productRepository.save(product);
        return "redirect:/product";
    }
}
