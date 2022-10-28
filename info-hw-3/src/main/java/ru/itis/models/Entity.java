package ru.itis.models;

import java.util.List;

public interface Entity {
    List<Object> entityAttributes();
    Long getEntityId();

}
