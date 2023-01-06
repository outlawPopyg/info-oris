package utils;

import java.sql.*;

public class Application {
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/library",
                "postgres",
                "outlaw"
        );

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from post_table");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("title"));
        }

    }
}
