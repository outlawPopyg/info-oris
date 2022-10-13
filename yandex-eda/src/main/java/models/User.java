package models;

import lombok.*;
import orm.annotations.ColumnName;
import orm.annotations.TableName;

@AllArgsConstructor
@Builder
@ToString
@Getter
@RequiredArgsConstructor
@TableName("Customer")
public class User implements Entity {
    @ColumnName(value = "id", primary = true, bigserial = true)
    private Long id;

    @ColumnName("login")
    private final String login;

    @ColumnName("password")
    private final String password;
}
