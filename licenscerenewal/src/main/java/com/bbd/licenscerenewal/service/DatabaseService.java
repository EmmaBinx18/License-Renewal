package com.bbd.licenscerenewal.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("DatabasePool")
public class DatabaseService implements IDataBasePool {
    private String connectionString = "Put connection string here";
    private List<Connection> connectionPool;
    private List<Connection> usedConnections =new ArrayList<Connection>();
    private static int MAX_POOL_SIZE = 100;
    private static int INITIAL_POOL_SIZE = 1;


    public DatabaseService(){
        System.out.println("Starting");
        connectionPool = new ArrayList<Connection>(INITIAL_POOL_SIZE);
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
                    throw new RuntimeException(
                            "Maximum pool size reached, no available connections!");
                }
            }

            Connection conn =  connectionPool.remove(connectionPool.size()-1);
            usedConnections.add(conn);
        System.out.println("returned the connection");
            return conn;
    }

    @Override
    public void ReleaseConnection(Connection conn) {
    connectionPool.add(conn);
    usedConnections.remove(conn);
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
