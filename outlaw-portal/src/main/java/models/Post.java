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
@TableName("PostModel")
public class Post implements Entity {
    @ColumnName(value = "id", primary = true, bigserial = true)
    private Long id;

    @ColumnName(value = "title", maxLength = 50)
    private String title;

    @ColumnName(value = "text", maxLength = 1200)
    private String text;

    @ColumnName("image_name")
    private String imageName;

    @ColumnName(value = "user_id", references = true, referenceTo = User.class)
    private Long userId;

    @ColumnName("post_image")
    private byte[] img;

    @ColumnName(value = "is_checked")
    private boolean isChecked;
}
