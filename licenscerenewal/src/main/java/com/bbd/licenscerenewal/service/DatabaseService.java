package com.bbd.licenscerenewal.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
@Qualifier("DatabasePool")
public class DatabaseService implements IDataBasePool {
    private String connectionString = "jdbc:sqlserver://license-renewal.database.windows.net;databaseName=production;user=grad-admin;password=Apple@jane56";
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int MAX_POOL_SIZE = 100;
    private static int INITIAL_POOL_SIZE = 1;

    public DatabaseService(){
        System.out.println("Starting");
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
        System.out.println("created pool");
    }

    @Override
    public Connection getConnection()  throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection conn =  connectionPool.remove(connectionPool.size()-1);
        usedConnections.add(conn);
        System.out.println("returned the connection");
        return conn;
    }

    @Override
    public void releaseConnection(Connection conn) {
        if(conn != null) {
            connectionPool.add(conn);
            usedConnections.remove(conn);
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(connectionString);
        }
        catch (SQLTimeoutException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //put method here for the DB stuff

}
