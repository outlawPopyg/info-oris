package web_filters;

import config.PostgresConnectionProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Entity;
import models.Product;
import models.User;
import orm.SQLGenerator;
import repositories.EntityRepository;
import repositories.ProductsRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebFilter(urlPatterns = "/products")
public class ProductFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {

            SQLGenerator<Product> productGenerator = new SQLGenerator<>();
            PreparedStatement statement = connection.prepareStatement(productGenerator.createTable(Product.class));
            statement.execute();

            EntityRepository repository = new ProductsRepository();
            HttpSession session = req.getSession(true);
            session.setAttribute("products", repository.findAll());

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        chain.doFilter(req, res);
    }
}
