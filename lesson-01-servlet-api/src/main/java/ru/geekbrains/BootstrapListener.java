package ru.geekbrains;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserRepository userRepository = new UserRepository();
        sce.getServletContext().setAttribute("userRepository", userRepository);

        userRepository.insert(new User("User1"));
        userRepository.insert(new User("User2"));
        userRepository.insert(new User("User3"));
    }
}
