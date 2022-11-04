package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.itis.helpers.ModelUtil;
import ru.itis.models.Book;
import ru.itis.models.Entity;

import javax.sql.DataSource;
import java.util.*;

public class BookRepositoryJdbcTemplateImpl implements EntityRepository {

    //language=SQL
    private final String SELECT_ALL_BOOKS = "select * from book";

    // language=SQL
    private final String DELETE_BOOK_BY_ID = "delete from book where id = :id";

    // language=SQL
    private final String GET_BOOK_BY_ID = "select * from book where id = :id";

    // language=SQL
    private final String UPDATE_BOOK = "update book set name = :name, publication_date = :publication_date, publishing = :publishing where id = :id";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Entity> bookMapper = ((row, rowNum) -> Book.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .publication_date(row.getObject("publication_date", Integer.class))
            .publishing(row.getString("publishing"))
            .build());

    @Override
    public List<Entity> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_BOOKS, bookMapper);
    }

    @Override
    public List<Entity> findAllSortedBy(String sortBy) {

        // language=SQL
        String SELECT_ALL_BOOK_SORTED_BY = "select * from book order by " + sortBy;

        return namedParameterJdbcTemplate.query(SELECT_ALL_BOOK_SORTED_BY, bookMapper);
    }

    @Override
    public void save(Map<String, String> paramsMap) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate());
        insert.withTableName("book").execute(paramsMap);
    }


    @Override
    public Optional<Entity> findById(Long id) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            GET_BOOK_BY_ID,
                            Collections.singletonMap("id", id),
                            bookMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Map<String,Object> paramsMap) {
        namedParameterJdbcTemplate.update(UPDATE_BOOK, paramsMap);
    }

    @Override
    public void delete(Long id) {
        namedParameterJdbcTemplate.update(DELETE_BOOK_BY_ID, Collections.singletonMap("id", id));
    }
}
