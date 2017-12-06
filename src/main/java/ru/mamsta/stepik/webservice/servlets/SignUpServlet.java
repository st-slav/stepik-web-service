package ru.mamsta.stepik.webservice.servlets;

import ru.mamsta.stepik.webservice.accountservice.AccountService;
import ru.mamsta.stepik.webservice.dbservice.DBExeption;
import ru.mamsta.stepik.webservice.dbservice.DBService;
import ru.mamsta.stepik.webservice.dbservice.dataset.UserDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private final DBService db;
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService, DBService db) {
        this.db = db;
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        if (login != null && !login.isEmpty() && pass != null && !pass.isEmpty()) {
            final UserDataSet user = new UserDataSet(request.getParameter("login").trim(), request.getParameter("password"));
            try {
                if (db.getUserByLogin(user.getLogin()) == null) {
                    db.addUser(user);
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    try {
                        response.getWriter().println("user with such login already exists");
                    } catch (IOException e) {
                        System.out.println("SignUpServlet.doPost - error: " + e.getMessage());
                    }
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                }
            } catch (DBExeption e) {
                System.out.println("SignUpServlet.doPost - error: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            System.out.println("SignInServlet.doPost - login or pass is empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
