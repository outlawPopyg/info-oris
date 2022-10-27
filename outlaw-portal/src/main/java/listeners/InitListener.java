package listeners;

import config.PostgresConnectionProvider;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;
import models.Comment;
import models.Post;
import models.User;
import orm.SQLGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebListener
public class InitListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public InitListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {

            SQLGenerator<User> userSQLGenerator = new SQLGenerator<>();
            SQLGenerator<Post> postSQLGenerator = new SQLGenerator<>();
            SQLGenerator<Comment> commentSQLGenerator = new SQLGenerator<>();

            PreparedStatement statement = connection.prepareStatement(userSQLGenerator.createTable(User.class));
            statement.execute();

            statement = connection.prepareStatement(postSQLGenerator.createTable(Post.class));
            statement.execute();

            statement = connection.prepareStatement(commentSQLGenerator.createTable(Comment.class));
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}