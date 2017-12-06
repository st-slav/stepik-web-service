package ru.mamsta.stepik.webservice.dbservice;

import org.h2.jdbcx.JdbcDataSource;
import ru.mamsta.stepik.webservice.dbservice.dao.UserDAO;
import ru.mamsta.stepik.webservice.dbservice.dataset.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class DBService {

    private Connection con;

    public DBService() {
        con = getH2connection();
    }

    private Connection getH2connection() {
        String url = "jdbc:h2:./stepik-server;INIT=runscript from 'classpath:sql-scripts/create.sql'";
        String user = "sa";
        String pass = "";
        try {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(user);
            ds.setPassword(pass);

//            Class.forName("org.h2.Driver");


//            return DriverManager.getConnection(url, user, pass);
            return ds.getConnection();
        } catch (SQLException e) {
            System.out.println("DBService.getH2connection: " + e.getMessage());
        }

        return null;
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + con.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + con.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + con.getMetaData().getDriverName());
            System.out.println("Autocommit: " + con.getAutoCommit());
        } catch (SQLException e) {
            System.out.println("DBService.printConnectInfo: " + e.getMessage());
        }
    }

    public UserDataSet getUserByLogin(String login) throws DBExeption {
        try {
            return new UserDAO(con).getByLogin(login);
        } catch (SQLException ex) {
            throw new DBExeption(ex);
        }
    }

    public long addUser(UserDataSet user) throws DBExeption {
        try {
            UserDAO userDao = new UserDAO(con);
            userDao.insertUser(user);
            return userDao.getId(user.getLogin());
        } catch (SQLException ex) {
            throw new DBExeption(ex);
        }
    }
}
