package com.bbd.licenscerenewal.service;


import java.sql.Connection;


public interface IDataBasePool {
    public Connection getConnection();

}
