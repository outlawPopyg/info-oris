package web_filters;

import config.PostgresConnectionProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import models.Product;
import models.User;
import orm.SQLGenerator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebFilter(urlPatterns = "/auth")
public class AuthFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {

            SQLGenerator<User> generator = new SQLGenerator<>();

            PreparedStatement statement = connection.prepareStatement(generator.createTable(User.class));
            statement.execute();

            User user1 = User.builder()
                    .login("roman_bag")
                    .password("legenda")
                    .build();

            User user2 = User.builder()
                    .login("outlaw")
                    .password("popyg")
                    .build();

            statement = connection.prepareStatement(generator.insert(user1));
            statement.execute();

            statement = connection.prepareStatement(generator.insert(user2));
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        chain.doFilter(req, res);
    }
}
