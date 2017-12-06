package ru.mamsta.stepik.webservice.dbservice.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    private Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public boolean execQuery(String query) throws SQLException {
        try(Statement st = connection.createStatement()) {
            return st.execute(query);
        } catch (SQLException e) {
            System.out.println("Executor.execQuery: " + e.getMessage() + "; query: " + query);
            throw e;
        }
    }

    public long updateQuery(String query) throws SQLException {
        try(Statement st = connection.createStatement()) {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Executor.updateQuery: " + e.getMessage() + "; query: " + query);
            throw e;
        }
    }

    public <T> T execQueryToResult(String query, ResultHandler<T> function) throws SQLException {
        try(Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            T t = function.get(rs);

            return t;
        } catch (SQLException e) {
            System.out.println("Executor.execQueryToResult: " + e.getMessage() + "; query: " + query);
            throw e;
        }
    }
}
