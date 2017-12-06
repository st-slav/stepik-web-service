package ru.mamsta.stepik.webservice.dbservice.dao;

import ru.mamsta.stepik.webservice.dbservice.dataset.UserDataSet;
import ru.mamsta.stepik.webservice.dbservice.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO {

    private Executor executor;

    public UserDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UserDataSet getByLogin(String login) throws SQLException {
        return executor.execQueryToResult("select * from sws.user u where u.user_login = '"+ login + "'", (rs) -> {
            if(rs.next()) {
                return new UserDataSet(rs.getLong("user_id"), rs.getString("user_login"), rs.getString("user_Password"));
            } else {
                return null;
            }
        });
    }

    public void insertUser(UserDataSet user) throws SQLException {
        executor.execQuery("insert into sws.user (user_login, user_password) values ('" + user.getLogin() +"', '" + user.getPassword() + "')");
    }

    public long getId(String login) throws SQLException {
        return executor.execQueryToResult("select u.user_id from sws.user u where u.user_login = '" + login + "'", rs -> {
            rs.next();
            return rs.getLong(1);
        });
    }
}
