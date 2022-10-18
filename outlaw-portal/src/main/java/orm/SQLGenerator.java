package orm;

import orm.annotations.ColumnName;
import orm.annotations.TableName;

import java.lang.reflect.Field;

public class SQLGenerator<T> {

    private String generateCreateSubRequest(ColumnName columnName, String sourceType) {

        String name = columnName.value();
        boolean bigSerial = columnName.bigserial();
        int maxLength = columnName.maxLength();
        boolean primary = columnName.primary();
        boolean defaultBoolean = columnName.defaultBoolean();
        boolean references = columnName.references();
        String defaultString = columnName.defaultString();
        Class<T> referenceTo = (Class<T>) columnName.referenceTo();

        switch (sourceType) {
            case "boolean":
                return name + " " + sourceType + " default " + defaultBoolean;
            case "Long":
                return name + (bigSerial ? " bigserial" : " bigint") +
                        (primary ? " primary key" : "") +
                        (references ? " references " + getTableName(referenceTo) : "");
            case "String":
                return name + " varchar(" + maxLength + ")" + (defaultString.length() > 0 ? "default '" + defaultString + "'" : "");
            case "Integer":
                return name + " integer";
            case "byte[]":
                return name + " bytea";
            default:
                return "";
        }
    }

    public String createTable(Class<T> entityClass) {
        String tableName = getTableName(entityClass);
        String SQL_REQUEST = "create table if not exists " + tableName + " ( ";

        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder subRequest = new StringBuilder();

        int i;
        for (i = 0; i < fields.length - 1; i++) {
            ColumnName columnName = fields[i].getDeclaredAnnotation(ColumnName.class);
            String fieldType = fields[i].getType().getSimpleName();

            subRequest.append(generateCreateSubRequest(columnName, fieldType)).append(",");
        }
        subRequest.append(
                generateCreateSubRequest(
                        fields[i].getDeclaredAnnotation(ColumnName.class),
                        fields[i].getType().getSimpleName()
                )
        );

        return SQL_REQUEST + subRequest + " )";
    }

    public String insert(Object entity) {
        String tableName = entity.getClass().getDeclaredAnnotation(TableName.class).value();
        Field[] fields = entity.getClass().getDeclaredFields();

        String SQL_REQUEST = "insert into " + tableName;

        StringBuilder attributes = new StringBuilder("(");
        StringBuilder values = new StringBuilder("values (");

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                ColumnName columnName = field.getDeclaredAnnotation(ColumnName.class);

                String fieldType = field.getType().getSimpleName();
                Object entityValue = field.get(entity);

                if (fieldType.equals("byte[]") && entityValue != null) {
                    entityValue = "?";
                }

                if (entityValue != null && fieldType.equals("String")) {
                    entityValue = "'" + entityValue + "'";
                }

                if (entityValue != null) {
                    attributes.append(columnName.value()).append(",");
                    values.append(entityValue).append(",");
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return SQL_REQUEST
                + generateSubRequestWithoutLastComma(attributes) + ") "
                + generateSubRequestWithoutLastComma(values) + ")";
    }

    public String update(Object entity) {
        String tableName = entity.getClass().getDeclaredAnnotation(TableName.class).value();

        String SqlRequest = "update " + tableName + " set ";
        StringBuilder subRequest = new StringBuilder(SqlRequest);

        Field[] fields = entity.getClass().getDeclaredFields();

        Long id = null;

        try {
            for (Field field : fields) {
                field.setAccessible(true);

                String fieldName = field.getDeclaredAnnotation(ColumnName.class).value();
                String fieldType = field.getType().getSimpleName();
                Object fieldValue = field.get(entity);

                if (fieldName.equals("id")) {
                    id = (Long) fieldValue;
                }

                if (fieldType.equals("byte[]") && fieldValue != null) {
                    fieldValue = "?";
                }

                if (fieldValue != null && fieldType.equals("String")) {
                    fieldValue = "'" + fieldValue + "'";
                }

                if (fieldValue != null) {
                    subRequest.append(fieldName).append(" = ").append(fieldValue).append(",");
                }

                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

        return generateSubRequestWithoutLastComma(subRequest) + " where id = " + id;
    }

    public String delete(Long id, Class<T> entityClass) {
        String tableName = getTableName(entityClass);
        return "delete from " + tableName + " where id = " + id;
    }

    public String find(Long id, Class<T> entityClass) {
        String tableName = getTableName(entityClass);
        return "select * from " + tableName + " where id = " + id;
    }

    public String findAll(Class<T> entityClass) {
        String tableName = getTableName(entityClass);
        return "select * from " + tableName;
    }

    private String generateSubRequestWithoutLastComma(StringBuilder builder) {
        return builder.substring(0, builder.length()-1);
    }

    private String getTableName(Class<T> aClass) {
        return aClass.getDeclaredAnnotation(TableName.class).value();
    }
}


