package ru.itis.servlets.book_author;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.config.PostgresConnectionProvider;
import ru.itis.models.Book;
import ru.itis.models.BookAuthor;
import ru.itis.models.Entity;
import ru.itis.repositories.BookAuthorRepositoryJdbcTemplateImpl;
import ru.itis.repositories.BookRepositoryJdbcTemplateImpl;
import ru.itis.repositories.EntityRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/bookauthor/sort")
public class SortBookAuthor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource dataSource = new PostgresConnectionProvider();
        EntityRepository bookAuthorRepository = new BookAuthorRepositoryJdbcTemplateImpl(dataSource);

        String sortBy = req.getParameter("sortBy");
        List<Entity> sortedList = bookAuthorRepository.findAllSortedBy(sortBy);

        req.setAttribute("entities", sortedList);
        req.setAttribute("aClass", BookAuthor.class);

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
