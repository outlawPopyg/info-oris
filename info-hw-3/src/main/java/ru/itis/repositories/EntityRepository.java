package ru.itis.repositories;

import ru.itis.models.Author;
import ru.itis.models.Entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EntityRepository {
    List<Entity> findAll();

    List<Entity> findAllSortedBy(String sortBy);
    Optional<Entity> findById(Long id);
    void update(Map<String, Object> paramsMap);
    void delete(Long id);
    void save(Map<String, String> paramsMap);
}
