package org.example.utils;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ConnectionPool {
    private static final String PASSWORD = PropertiesUtil.get("password");
    private static final String USERNAME = PropertiesUtil.get("username");
    private static final String URL = PropertiesUtil.get("url");
    private static final String DRIVER_NAME = PropertiesUtil.get("driverClassName");
    private static final String POOL_SIZE = PropertiesUtil.get("size");
    public static final Integer DEFAULT_POOL_SIZE = 1;
    private static final BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setInitialSize(POOL_SIZE == null ? DEFAULT_POOL_SIZE : Integer.parseInt(POOL_SIZE));
        dataSource.setMinIdle(DEFAULT_POOL_SIZE);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
