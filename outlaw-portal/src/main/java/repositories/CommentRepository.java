package repositories;

import config.PostgresConnectionProvider;
import lombok.Cleanup;
import models.Comment;
import models.Entity;
import orm.SQLGenerator;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements EntityRepository {

    private final SQLGenerator<Comment> generator = new SQLGenerator<>();

    @Override
    public Entity save(Entity entity) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            Comment comment = (Comment) entity;

            PreparedStatement statement = connection.prepareStatement(generator.insert(entity), Statement.RETURN_GENERATED_KEYS);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating comment error, no rows were affected");
            }

            @Cleanup
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                comment.setId(generatedKeys.getLong("id"));
            }

            return comment;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<? extends Entity> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.findAll(Comment.class));

            @Cleanup
            ResultSet resultSet = statement.executeQuery();

            List<Comment> list = new LinkedList<>();

            while (resultSet.next()) {
                Comment comment = Comment.builder()
                        .id(resultSet.getLong("id"))
                        .text(resultSet.getString("text"))
                        .postId(resultSet.getLong("post_id"))
                        .userId(resultSet.getLong("user_id"))
                        .build();
                list.add(comment);
            }

            return list;

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
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(generator.delete(id, Comment.class));
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Entity entity) {

    }
}
