package orm;

import models.Post;
import models.User;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        Field[] fields = Post.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType().getSimpleName());
        }
    }
}
