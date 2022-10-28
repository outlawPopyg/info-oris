package ru.itis.servlets.book_author;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.config.PostgresConnectionProvider;
import ru.itis.helpers.ModelUtil;
import ru.itis.models.BookAuthor;
import ru.itis.models.Entity;
import ru.itis.repositories.BookAuthorRepositoryJdbcTemplateImpl;
import ru.itis.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.repositories.EntityRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/bookauthor")
public class BookAuthorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository bookAuthorRepository = new BookAuthorRepositoryJdbcTemplateImpl(dataSource);

        List<Entity> books = bookAuthorRepository.findAll();

        req.setAttribute("entities", books);
        req.setAttribute("aClass", BookAuthor.class);

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository bookAuthorRepository = new BookAuthorRepositoryJdbcTemplateImpl(dataSource);

        ModelUtil.handleUpdateAndDeleteAndRedirect(bookAuthorRepository, "/bookauthor", req, resp);
    }
}
