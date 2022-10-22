package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Product;
import repositories.EntityRepository;
import repositories.ProductRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/shop")
public class Products extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityRepository repository = new ProductRepository();
        List<Product> products = (List<Product>) repository.findAll();

        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(req, resp);
    }
}
