package org.example.utils;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String PASSWORD = PropertiesUtil.get("password");
    private static final String USERNAME = PropertiesUtil.get("username");
    private static final String URL = PropertiesUtil.get("url");
    private static final String POOL_SIZE = PropertiesUtil.get("size");
    public static final Integer DEFAULT_POOL_SIZE = 10;
    private static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setInitialSize(Integer.parseInt(POOL_SIZE));
        dataSource.setMinIdle(DEFAULT_POOL_SIZE);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
