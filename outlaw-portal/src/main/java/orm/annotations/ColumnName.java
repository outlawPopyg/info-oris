package orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnName {
    String value();
    boolean primary() default false;
    boolean bigserial() default false;
    int maxLength() default 255;
    boolean defaultBoolean() default false;
    boolean references() default false;
    String defaultString() default "";
    Class<?> referenceTo() default Class.class;
}

