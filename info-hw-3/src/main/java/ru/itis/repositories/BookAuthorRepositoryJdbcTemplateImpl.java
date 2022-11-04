package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.itis.models.Author;
import ru.itis.models.BookAuthor;
import ru.itis.models.Entity;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookAuthorRepositoryJdbcTemplateImpl implements EntityRepository {

    // language=SQL
    private static final String SELECT_ALL_BOOK_AUTHOR = "select * from book_author";

    // language=SQL
    private static final String FIND_BOOK_AUTHOR_BY_ID = "select * from book_author where id = :id";

    // language=SQL
    private static final String DELETE_BOOK_AUTHOR = "delete from book_author where id = :id";

    // language=SQL
    private static final String UPDATE_BOOK_AUTHOR = "update book_author set book_id = :book_id, author_id = :author_id where id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookAuthorRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Entity> bookAuthorMapper = ((row, rowNum) -> BookAuthor.builder()
            .id(row.getLong("id"))
            .author_id(row.getLong("author_id"))
            .book_id(row.getLong("book_id"))
            .build());

    @Override
    public List<Entity> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_BOOK_AUTHOR, bookAuthorMapper);
    }

    @Override
    public List<Entity> findAllSortedBy(String sortBy) {
        // language=SQL
        String SELECT_ALL_BOOK_AUTHOR_ORDER_BY = "select * from book_author order by " + sortBy;
        return namedParameterJdbcTemplate.query(SELECT_ALL_BOOK_AUTHOR_ORDER_BY, bookAuthorMapper);
    }

    @Override
    public Optional<Entity> findById(Long id) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            FIND_BOOK_AUTHOR_BY_ID,
                            Collections.singletonMap("id", id),
                            bookAuthorMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Map<String, Object> paramsMap) {
        namedParameterJdbcTemplate.update(UPDATE_BOOK_AUTHOR, paramsMap);
    }

    @Override
    public void delete(Long id) {
        namedParameterJdbcTemplate.update(DELETE_BOOK_AUTHOR, Collections.singletonMap("id", id));
    }

    @Override
    public void save(Map<String, String> paramsMap) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate());
        insert.withTableName("book_author").execute(paramsMap);
    }
}
