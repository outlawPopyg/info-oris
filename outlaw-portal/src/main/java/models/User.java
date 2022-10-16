package models;

import lombok.*;
import orm.annotations.ColumnName;
import orm.annotations.TableName;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@TableName("UserModel")
public class User implements Entity {

    @ColumnName(value = "id", primary = true, bigserial = true)
    private Long id;

    @ColumnName("login")
    private final String login;

    @ColumnName("password")
    private final String password;

    @ColumnName(value = "entity_role", defaultString = "user")
    private final String role;
}
