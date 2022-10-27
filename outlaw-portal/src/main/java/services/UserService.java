package services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Entity;
import models.User;
import repositories.EntityRepository;
import repositories.UserRepository;
import security.Hash;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static final EntityRepository repository = new UserRepository();

    public void signUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        password = Hash.hashPassword(password).toLowerCase();

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
    }

    public void logIn(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = Hash.hashPassword(req.getParameter("password")).toLowerCase();
        HttpSession session = req.getSession(true);

        EntityRepository repository = new UserRepository();
        List<User> entities = (List<User>) repository.findAll();

        for (User user : entities) {
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

    public static String getUserNameByPostId(Long id) {
        Optional<Entity> optional = repository.findById(id);

        if (optional.isPresent()) {
            User user = (User) optional.get();
            return user.getLogin();
        } else {
            throw new IllegalArgumentException("No such user");
        }
    }

    public User getUser(Long id) {
        Optional<Entity> optional = repository.findById(id);

        if (optional.isPresent()) {
            return (User) optional.get();
        } else {
            throw new IllegalArgumentException("No such user");
        }
    }
}
