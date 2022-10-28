package ru.itis.servlets.book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.config.PostgresConnectionProvider;
import ru.itis.helpers.ModelUtil;
import ru.itis.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.repositories.EntityRepository;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(value = "/book/add")
public class AddBook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ModelUtil.getFieldsFromEntityAndSetAsRequestAttribute(req);

        req.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository bookRepository = new BookRepositoryJdbcTemplateImpl(dataSource);

        try {
            ModelUtil.addEntity(bookRepository, req);
        } catch (Exception e) {
            ModelUtil.handleException(e, req, resp);
        }

        resp.sendRedirect("/book");
    }

}
