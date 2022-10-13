package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listeners.SessionCreatedListener;
import models.Entity;
import models.Product;
import models.User;
import repositories.EntityRepository;
import repositories.ProductsRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet("/products")
public class ProductList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] added = req.getParameterValues("added");
        EntityRepository repository = new ProductsRepository();

        if (added.length == 0) {
            return;
        }

        for (String id : added) {
            Optional<Entity> product = repository.findById(Long.parseLong(id));
            product.ifPresent(entity -> SessionCreatedListener.addOrder((Product) entity));
        }


        resp.sendRedirect("/cart");

    }
}
