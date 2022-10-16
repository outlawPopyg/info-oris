package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Entity;
import models.User;
import repositories.EntityRepository;
import repositories.UserRepository;
import security.Hash;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/reg")
public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        password = Hash.hashPassword(password).toLowerCase();

        EntityRepository repository = new UserRepository();

        List<User> users = (List<User>) repository.findAll();
        boolean alreadyRegistered = users.stream()
                .anyMatch(user -> user.getLogin().equals(login));

        if (alreadyRegistered) {
            req.setAttribute("errorMessage", "Пользователь с именем " + login + " уже существует");
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
            return;
        }

        User user = User.builder().login(login).password(password).build();
        repository.save(user);

        resp.sendRedirect("/reg");
    }
}
