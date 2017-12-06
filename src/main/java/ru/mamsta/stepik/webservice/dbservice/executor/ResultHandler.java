package ru.mamsta.stepik.webservice.dbservice.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler<T> {

    T get(ResultSet rs) throws SQLException;
}
