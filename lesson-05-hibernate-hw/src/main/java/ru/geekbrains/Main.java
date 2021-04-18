package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // Users

        User[] usersArray = {
                new User(null, "beta_user2", "password"),
                new User(null, "alpha_user3", "password"),
                new User(null, "new_user4", "password"),
                new User(null, "arg_user4", "password"),
                new User(null, "al_user4", "password")
        };

        UserRepository userRepository = new UserRepository();

        for (User usr: usersArray) {
            userRepository.saveOrUpdate(usr);
        }

        System.out.println("One user by id");
        User user = userRepository.findById(1L);
        System.out.println(user.getId() + "\t" + user.getUsername() + "\t" + user.getPassword());

        System.out.println("All users");
        List<User> users = userRepository.findAll();

        users.forEach(usr -> System.out.println(usr.getId() + "\t" + usr.getUsername() + "\t" + usr.getPassword()));

        System.out.println("Delete user with id = 5");
        userRepository.deleteById(5L);
        users = userRepository.findAll();
        users.forEach(usr -> System.out.println(usr.getId() + "\t" + usr.getUsername() + "\t" + usr.getPassword()));

        System.out.println("Save new user");
        user = userRepository.saveOrUpdate(new User(null, "MegaUserXXX", "MegaPasswordXXX"));
        System.out.println(user.getId() + "\t" + user.getUsername() + "\t" + user.getPassword());

        System.out.println("Update user with id = 3");
        user = userRepository.saveOrUpdate(new User(3L, "NoUser_0", "NoPass1"));
        System.out.println(user.getId() + "\t" + user.getUsername() + "\t" + user.getPassword());


        // Products

        Product[] productsArray = {
                new Product(null, "Product1", new BigDecimal(100.00)),
                new Product(null, "Product2", new BigDecimal(200.00)),
                new Product(null, "Product3", new BigDecimal(300.00)),
                new Product(null, "Product4", new BigDecimal(400.00)),
                new Product(null, "Product5", new BigDecimal(500.00)),
        };

        ProductRepository productRepository = new ProductRepository();

        for (Product prod: productsArray) {
            productRepository.saveOrUpdate(prod);
        }

        System.out.println("One product by id");
        Product product = productRepository.findById(1L);
        System.out.println(product.getId() + "\t" + product.getProductname() + "\t" + product.getProductCost());

        System.out.println("All products");
        List<Product> products = productRepository.findAll();

        products.forEach(prod -> System.out.println(prod.getId() + "\t" + prod.getProductname() + "\t" +
                prod.getProductCost()));

        System.out.println("Delete product with id = 5");
        productRepository.deleteById(5L);
        products = productRepository.findAll();
        products.forEach(prod -> System.out.println(prod.getId() + "\t" + prod.getProductname() + "\t" +
                prod.getProductCost()));

        System.out.println("Save new product");
        product = productRepository.saveOrUpdate(new Product(null, "ProductXXX", new BigDecimal(55)));
        System.out.println(product.getId() + "\t" + product.getProductname() + "\t" + product.getProductCost());

        System.out.println("Update product with id = 3");
        product = productRepository.saveOrUpdate(new Product(3L, "NewProduct3", new BigDecimal(55)));
        System.out.println(product.getId() + "\t" + product.getProductname() + "\t" + product.getProductCost());
    }
}
