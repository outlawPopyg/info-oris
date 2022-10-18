package repositories;

import config.PostgresConnectionProvider;
import lombok.Cleanup;
import models.Entity;
import models.Post;
import orm.SQLGenerator;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PostsRepository implements EntityRepository {

    SQLGenerator<Post> generator = new SQLGenerator<>();

    @Override
    public Post save(Entity entity) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            Post post = (Post) entity;

            PreparedStatement statement = connection.prepareStatement(generator.insert(post), Statement.RETURN_GENERATED_KEYS);
            System.out.println(generator.insert(post));

            if (post.getImg() != null) {
                statement.setBytes(1, post.getImg());
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating post failed, no rows affected");
            }

            @Cleanup
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getLong("id"));
            }

            return post;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<? extends Entity> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.findAll(Post.class));
            @Cleanup
            ResultSet resultSet = statement.executeQuery();

            List<Entity> resultList = new LinkedList<>();

            while (resultSet.next()) {
                Post post = Post.builder()
                        .id(resultSet.getLong("id"))
                        .text(resultSet.getString("text"))
                        .title(resultSet.getString("title"))
                        .imageName(resultSet.getString("image_name"))
                        .img(resultSet.getBytes("post_image"))
                        .isChecked(resultSet.getBoolean("is_checked"))
                        .userId(resultSet.getLong("user_id"))
                        .build();
                resultList.add(post);
            }

            return resultList;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Entity> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.find(id, Post.class));
            ResultSet resultSet = statement.executeQuery();

            Entity post;
            if (resultSet.next()) {
                post = Post.builder()
                        .id(resultSet.getLong("id"))
                        .text(resultSet.getString("text"))
                        .title(resultSet.getString("title"))
                        .imageName(resultSet.getString("image_name"))
                        .img(resultSet.getBytes("post_image"))
                        .isChecked(resultSet.getBoolean("is_checked"))
                        .userId(resultSet.getLong("user_id"))
                        .build();

                return Optional.of(post);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.delete(id, Post.class));
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Entity entity) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.update(entity));

            Post post = (Post) entity;
            if (post.getImg() != null) {
                statement.setBytes(1, post.getImg());
            }

            statement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
