package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import orm.annotations.ColumnName;
import orm.annotations.TableName;

@AllArgsConstructor
@Builder
@ToString
@RequiredArgsConstructor
@TableName("Order")
public class Order implements Entity {
    @ColumnName(value = "id", primary = true, bigserial = true)
    private Long id;

    @ColumnName(value = "date")
    private String date;

    @ColumnName(value = "position")
    private Integer position;

}
