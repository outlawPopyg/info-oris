package web_filters;

import config.PostgresConnectionProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import models.Post;
import models.Product;
import models.User;
import orm.SQLGenerator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebFilter(urlPatterns = "*")
public class HomeWebFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {

            int port = req.getLocalPort();
            req.setAttribute("port", port);

            SQLGenerator<User> userSQLGenerator = new SQLGenerator<>();
            SQLGenerator<Post> postSQLGenerator = new SQLGenerator<>();
            SQLGenerator<Product> productSQLGenerator = new SQLGenerator<>();

            PreparedStatement statement = connection.prepareStatement(userSQLGenerator.createTable(User.class));
            statement.execute();

            statement = connection.prepareStatement(postSQLGenerator.createTable(Post.class));
            statement.execute();

            statement = connection.prepareStatement(productSQLGenerator.createTable(Product.class));
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        chain.doFilter(req, res);
    }
}
