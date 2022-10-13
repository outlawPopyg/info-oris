package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import listeners.SessionCreatedListener;
import models.Entity;
import models.User;
import orm.SQLGenerator;
import repositories.EntityRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/auth")
public class Authorization extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession(true);

        EntityRepository repository = new UserRepository();
        List<Entity> userList = repository.findAll();

        for (Entity entity : userList) {
            User user = (User) entity;
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                SessionCreatedListener.AUTH_USERS.add(user);
                SessionCreatedListener.IS_AUTH = true;
                resp.sendRedirect("/products");
                return;
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp").forward(req, resp);
    }
}
