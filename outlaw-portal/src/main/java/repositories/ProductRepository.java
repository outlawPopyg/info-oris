package repositories;

import config.PostgresConnectionProvider;
import lombok.Cleanup;
import models.Entity;
import models.Product;
import orm.SQLGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductRepository implements EntityRepository {

    private final SQLGenerator<Product> generator = new SQLGenerator<>();

    @Override
    public Entity save(Entity entity) {
        return null;
    }

    @Override
    public List<? extends Entity> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.findAll(Product.class));

            @Cleanup
            ResultSet resultSet = statement.executeQuery();
            List<Product> resultList = new LinkedList<>();

            while (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getObject("price", Integer.class))
                        .imgPath(resultSet.getString("img_path"))
                        .build();

                resultList.add(product);
            }

            return resultList;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Entity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Entity entity) {

    }
}
