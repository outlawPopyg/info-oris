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
public class Book implements Entity {
    private Long id;
    private String name;
    private Integer publication_date;

    private String publishing;

    @Override
    public List<Object> entityAttributes() {
        List<Object> entityList = new LinkedList<>();
        entityList.add(id);
        entityList.add(name);
        entityList.add(publication_date);
        entityList.add(publishing);

        return entityList;
    }

    @Override
    public Long getEntityId() {
        return this.getId();
    }

}
