package ru.itis.config;

import jdk.jshell.spi.ExecutionControl;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class PostgresConnectionProvider implements DataSource {
    private final String url;
    private final String userName;
    private final String password;

    public PostgresConnectionProvider() {
        this.url = PostgresDBCConnectionDataHolder.URL;
        this.userName = PostgresDBCConnectionDataHolder.USERNAME;
        this.password = PostgresDBCConnectionDataHolder.PASSWORD;
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName(PostgresDBCConnectionDataHolder.DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find driver class");
        }

        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("unable to receive exception");
            throw new RuntimeException();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public PrintWriter getLogWriter() throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

}
