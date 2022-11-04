package ru.itis.servlets.author;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.config.PostgresConnectionProvider;
import ru.itis.models.Author;
import ru.itis.models.Book;
import ru.itis.models.Entity;
import ru.itis.repositories.AuthorRepositoryJdbcTemplateImpl;
import ru.itis.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.repositories.EntityRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/author/sort")
public class SortAuthor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository authorRepository = new AuthorRepositoryJdbcTemplateImpl(dataSource);

        String sortBy = req.getParameter("sortBy");
        List<Entity> sortedList = authorRepository.findAllSortedBy(sortBy);

        req.setAttribute("entities", sortedList);
        req.setAttribute("aClass", Author.class);

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
