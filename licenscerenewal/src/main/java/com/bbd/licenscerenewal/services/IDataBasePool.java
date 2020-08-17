package com.bbd.licenscerenewal.services;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDataBasePool {
    public Connection getConnection() throws SQLException;
    public void releaseConnection(Connection conn);
}
