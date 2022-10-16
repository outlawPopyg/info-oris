package repositories;

import config.PostgresConnectionProvider;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import models.Entity;
import models.User;
import orm.SQLGenerator;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements EntityRepository {

    private final SQLGenerator<User> generator = new SQLGenerator<>();

    @Override
    public User save(Entity entity) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            User user = (User) entity;

            PreparedStatement statement = connection.prepareStatement(generator.insert(entity), Statement.RETURN_GENERATED_KEYS);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected");
            }

            @Cleanup
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }

            return user;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<? extends Entity> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.findAll(User.class));
            ResultSet resultSet = statement.executeQuery();

            List<Entity> resultLis = new LinkedList<>();

            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .role(resultSet.getString("entity_role"))
                        .build();
                resultLis.add(user);
            }
            return resultLis;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Entity> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.find(id, User.class));
            ResultSet resultSet = statement.executeQuery();

            Entity user;
            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .role(resultSet.getString("entity_role"))
                        .build();

                return Optional.of(user);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.delete(id, User.class));
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Entity entity) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.update(entity));
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
