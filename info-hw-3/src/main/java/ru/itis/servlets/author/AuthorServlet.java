package ru.itis.servlets.author;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.config.PostgresConnectionProvider;
import ru.itis.helpers.ModelUtil;
import ru.itis.models.Author;
import ru.itis.models.Book;
import ru.itis.models.Entity;
import ru.itis.repositories.AuthorRepositoryJdbcTemplateImpl;
import ru.itis.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.repositories.EntityRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/author")
public class AuthorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository authorRepository = new AuthorRepositoryJdbcTemplateImpl(dataSource);

        List<Entity> books = authorRepository.findAll();

        req.setAttribute("entities", books);
        req.setAttribute("aClass", Author.class);

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository authorRepository = new AuthorRepositoryJdbcTemplateImpl(dataSource);

        ModelUtil.handleUpdateAndDeleteAndRedirect(authorRepository, "/author", req, resp);
    }
}
