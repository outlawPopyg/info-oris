package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.itis.models.Author;
import ru.itis.models.Book;
import ru.itis.models.Entity;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthorRepositoryJdbcTemplateImpl implements EntityRepository {

    // language=SQL
    private static final String SQL_SELECT_ALL_AUTHORS = "select * from author";

    // language=SQL
    private static final String SQL_DELETE_AUTHOR_BY_ID = "delete from author where id = :id";

    // language=SQL
    private static final String SQL_GET_AUTHOR_BY_ID = "select * from author where id = :id";

    // language=SQL
    private static final String SQL_UPDATE_AUTHOR = "update author set name = :name where id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthorRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Entity> authorMapper = ((row, rowNum) -> Author.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .build());

    @Override
    public List<Entity> findAll() {
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL_AUTHORS, authorMapper);
    }

    @Override
    public List<Entity> findAllSortedBy(String sortBy) {
        // language=SQL
        String SQL_FIND_ALL_SORTED_BY = "select * from author order by " + sortBy;
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL_SORTED_BY, authorMapper);
    }

    @Override
    public Optional<Entity> findById(Long id) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL_GET_AUTHOR_BY_ID,
                            Collections.singletonMap("id", id),
                            authorMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Map<String,Object> paramsMap) {
        namedParameterJdbcTemplate.update(SQL_UPDATE_AUTHOR, paramsMap);
    }

    @Override
    public void delete(Long id) {
        namedParameterJdbcTemplate.update(SQL_DELETE_AUTHOR_BY_ID, Collections.singletonMap("id", id));
    }

    @Override
    public void save(Map<String, String> paramsMap) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate());
        insert.withTableName("author").execute(paramsMap);
    }
}
