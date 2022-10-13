package repositories;

import models.Entity;

import java.util.List;
import java.util.Optional;

public interface EntityRepository {
    void save(Entity entity);
    List<Entity> findAll();
    Optional<Entity> findById(Long id);
    void delete(Long id);
    void update(Entity entity);
}

