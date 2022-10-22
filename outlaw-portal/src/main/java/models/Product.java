package models;

import lombok.*;
import orm.annotations.ColumnName;
import orm.annotations.TableName;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("Product")
public class Product implements Entity {
    @ColumnName(value = "id", primary = true, bigserial = true)
    private Long id;

    @ColumnName("name")
    private String name;

    @ColumnName("price")
    private Integer price;

    @ColumnName("img_path")
    private String imgPath;
}
