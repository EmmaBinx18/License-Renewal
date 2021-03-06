package com.bbd.licenscerenewal.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    public T add(T toAdd) throws SQLException;
    public List<T> convertResultSet(ResultSet toConvert) throws SQLException;
    public T getById(int id) throws SQLException;
}
