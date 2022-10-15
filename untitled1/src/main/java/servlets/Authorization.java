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
import repositories.EntityRepository;
import repositories.UserRepository;
import security.Hash;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/auth")
public class Authorization extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/auth.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = Hash.hashPassword(req.getParameter("password")).toLowerCase();
        HttpSession session = req.getSession(true);

        EntityRepository repository = new UserRepository();
        List<Entity> entities = repository.findAll();

        for (Entity entity : entities) {
            User user = (User) entity;
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                Optional<Entity> optional = repository.findById(user.getId());
                optional.ifPresent(value -> session.setAttribute("authUser", (User) value));
                session.setAttribute("isAuth", true);
                resp.sendRedirect("/home");
                return;
            }
        }

        req.setAttribute("errorMessage", "Неправильный логин или пароль");
        req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
    }
}
