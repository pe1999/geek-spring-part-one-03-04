package ru.geekbrains;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/first-http-servlet/*")
public class FirstHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        resp.getWriter().println("<h1>Привет от сервлета!!!</h1>");
        resp.getWriter().println("<p>ContextPath: " + req.getContextPath() + "</p>");
        resp.getWriter().println("<p>ServletPath: " + req.getServletPath() +"</p>");
        resp.getWriter().println("<p>PathInfo: " + req.getPathInfo() + "</p>");
        resp.getWriter().println("<p>QueryString: " + req.getQueryString() + "</p>");
        resp.getWriter().println("<p>param1: " + req.getParameter("param1") + "</p>");
        resp.getWriter().println("<p>param2: " + req.getParameter("param2") + "</p>");
        resp.getWriter().println("<p>header: " + req.getHeader("User-Agent") + "</p>");

        resp.setHeader("Some-Header", "Some-Value");
    }
}
