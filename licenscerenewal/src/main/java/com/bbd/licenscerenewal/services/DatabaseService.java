package com.bbd.licenscerenewal.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("DatabasePool")
public class DatabaseService implements IDataBasePool {
    private String connectionString = "jdbc:sqlserver://license-renewal.database.windows.net;databaseName=production;user=grad-admin;password=Apple@jane56";
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int MAX_POOL_SIZE = 100;
    private static int INITIAL_POOL_SIZE = 1;

    public DatabaseService() throws SQLException {
        System.out.println("Starting");
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for(int i = 0; i < INITIAL_POOL_SIZE; i++){
            connectionPool.add(createConnection());
        }
        System.out.println("Created Pool");
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(connectionPool.isEmpty()) {
            if(usedConnections.size() < MAX_POOL_SIZE){
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection conn = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(conn);
        System.out.println("Returned the connection");
        return conn;
    }

    @Override
    public void releaseConnection(Connection conn) {
        if(conn != null) {
            connectionPool.add(conn);
            usedConnections.remove(conn);
        }
    }

    private Connection createConnection() throws SQLException {
        try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLTimeoutException exception) {
            exception.printStackTrace();
            throw exception;
        } catch (SQLException exception){
            exception.printStackTrace();
            throw exception;
        }

    }
}