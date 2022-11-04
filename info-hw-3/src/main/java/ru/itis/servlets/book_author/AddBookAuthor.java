package ru.itis.servlets.book_author;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.config.PostgresConnectionProvider;
import ru.itis.helpers.ModelUtil;
import ru.itis.repositories.BookAuthorRepositoryJdbcTemplateImpl;
import ru.itis.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.repositories.EntityRepository;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(value = "/bookauthor/add")
public class AddBookAuthor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ModelUtil.getFieldsFromEntityAndSetAsRequestAttribute(req);

        req.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository bookAuthorRepository = new BookAuthorRepositoryJdbcTemplateImpl(dataSource);

        try {
            ModelUtil.addEntity(bookAuthorRepository, req);
        } catch (Exception e) {
            ModelUtil.handleException(e, req, resp);
        }

        resp.sendRedirect("/bookauthor");
    }
}
