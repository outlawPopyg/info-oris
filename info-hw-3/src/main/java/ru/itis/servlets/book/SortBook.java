package ru.itis.servlets.book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.config.PostgresConnectionProvider;
import ru.itis.models.Book;
import ru.itis.models.Entity;
import ru.itis.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.repositories.EntityRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/book/sort")
public class SortBook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository bookRepository = new BookRepositoryJdbcTemplateImpl(dataSource);

        String sortBy = req.getParameter("sortBy");
        List<Entity> sortedList = bookRepository.findAllSortedBy(sortBy);

        req.setAttribute("entities", sortedList);
        req.setAttribute("aClass", Book.class);

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
