package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ProductRepository productRepository = context.getBean("productRepository", ProductRepository.class);
        productRepository = context.getBean("productRepository", ProductRepository.class);
        productRepository = context.getBean("productRepository", ProductRepository.class);

        productRepository.insert(new Product("Product1Name", "Product1Description", new BigDecimal(100)));
        productRepository.insert(new Product("Product2Name", "Product2Description", new BigDecimal(200)));
        productRepository.insert(new Product("Product3Name", "Product3Description", new BigDecimal(300)));

        Cart cart1 = context.getBean("cart", Cart.class);
        Cart cart2 = context.getBean("cart", Cart.class);
        Cart cart3 = context.getBean("cart", Cart.class);

        List<Product> productList = productRepository.findAll();

        cart1.addToCart(productList.get(0).getId());
        cart2.addToCart(productList.get(1).getId());
        cart3.addToCart(productList.get(2).getId());

        System.out.println(cart1.getProductList().toString());
        System.out.println(cart2.getProductList().toString());
        System.out.println(cart3.getProductList().toString());
    }
}
