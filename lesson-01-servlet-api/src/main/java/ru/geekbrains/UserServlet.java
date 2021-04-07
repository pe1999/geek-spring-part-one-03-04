package ru.geekbrains;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {

    private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)$");

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            wr.println("<table>");
            wr.println("<tr>");
            wr.println("<th>Id</th>");
            wr.println("<th>Username</a></th>");
            wr.println("</tr>");

            for (User user : userRepository.findAll()) {
                wr.println("<tr>");
                wr.println("<td>" + user.getId() + "</td>");
                wr.println("<td><a href=\"" + req.getContextPath() + "/user/" + user.getId() + "\">" +
                        user.getUsername() + "</a></td>");
                wr.println("</tr>");
            }
            wr.println("</table>");
        } else {

            Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());

            if (!matcher.matches()) {
                wr.println("<h1>Пользователь не существует</h1>" +
                        "<p>Некорректный id пользователя: " +
                        req.getPathInfo().substring(1) + "</p>");
                resp.setStatus(400);
            } else {
                long id = Long.parseLong(matcher.group(1));
                User user = userRepository.findById(id);
                if (user == null) {
                    wr.println("<h1>Пользователь не существует</h1>" +
                            "<p>Не существует пользователя с id = " +
                            req.getPathInfo().substring(1) + "</p>");
                    resp.setStatus(404);
                } else {
                    wr.println("<h1>Карточка пользователя</h1>");
                    wr.println("<p>Имя пользователя: " + user.getUsername() + "</p>");
                    wr.println("<p>Id пользователя: " + user.getId() + "</p>");
                }
            }
        }
    }
}
