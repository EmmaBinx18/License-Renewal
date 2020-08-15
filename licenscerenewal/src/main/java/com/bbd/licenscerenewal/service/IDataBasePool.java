package com.bbd.licenscerenewal.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDataBasePool {
    public Connection getConnection() throws SQLException;
    public void ReleaseConnection(Connection conn);
}
