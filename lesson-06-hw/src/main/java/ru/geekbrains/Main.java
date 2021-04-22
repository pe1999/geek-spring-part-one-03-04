package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerProductService;
import ru.geekbrains.persist.Product;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CustomerProductService customerProductService =
                context.getBean("customerProductService", CustomerProductService.class);

        for (Product product: customerProductService.getProductListByCustomerId(5L)) {
            System.out.println(product.getProductId());
        }

        for (Customer customer: customerProductService.getCustomerListByProductId(5L)) {
            System.out.println(customer.getCustomerId());
        }

    }

}
