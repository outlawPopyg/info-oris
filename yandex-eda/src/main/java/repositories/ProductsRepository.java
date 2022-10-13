package repositories;

import config.PostgresConnectionProvider;
import models.Entity;
import models.Product;
import models.User;
import orm.SQLGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsRepository implements EntityRepository {

    private final SQLGenerator<Product> generator = new SQLGenerator<>();

    @Override
    public void save(Entity entity) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.insert(entity));
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Entity> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.findAll(Product.class));
            ResultSet resultSet = statement.executeQuery();

            List<Entity> resultLis = new LinkedList<>();

            while (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getObject("price", Integer.class))
                        .build();
                resultLis.add(product);
            }
            return resultLis;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Entity> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.find(id, Product.class));
            ResultSet resultSet = statement.executeQuery();

            Entity product;
            if (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getObject("price", Integer.class))
                        .build();

                return Optional.of(product);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.delete(id, Product.class));
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
