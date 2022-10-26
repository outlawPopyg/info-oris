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
@TableName("Comment")
public class Comment implements Entity {
    @ColumnName(value = "id", primary = true, bigserial = true)
    private Long id;

    @ColumnName("text")
    private String text;

    @ColumnName(value = "user_id", references = true, referenceTo = User.class)
    private Long userId;

    @ColumnName(value = "post_id", references = true, referenceTo = Post.class)
    private Long postId;
}
