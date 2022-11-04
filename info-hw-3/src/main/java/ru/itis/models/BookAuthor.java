package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookAuthor implements Entity {

    private Long id;
    private Long author_id;
    private Long book_id;

    @Override
    public List<Object> entityAttributes() {
        List<Object> entityList = new LinkedList<>();
        entityList.add(id);
        entityList.add(author_id);
        entityList.add(book_id);

        return entityList;
    }

    @Override
    public Long getEntityId() {
        return this.getId();
    }
}
