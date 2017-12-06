package ru.mamsta.stepik.webservice.servlets;

import ru.mamsta.stepik.webservice.accountservice.AccountService;
import ru.mamsta.stepik.webservice.dbservice.DBExeption;
import ru.mamsta.stepik.webservice.dbservice.DBService;
import ru.mamsta.stepik.webservice.dbservice.dataset.UserDataSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final AccountService accountService;
    private final DBService db;

    public SignInServlet(AccountService accountService, DBService db) {
        this.db = db;
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        if (login != null && !login.isEmpty() && pass != null && !pass.isEmpty()) {
            final UserDataSet requestUser = new UserDataSet(login, pass);
            try {
                final UserDataSet user = db.getUserByLogin(requestUser.getLogin());
                if (user != null && user.getPassword().equals(requestUser.getPassword())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.addCookie(new Cookie("id", accountService.autorization(user)));
                    response.getWriter().println("Authorized: " + user.getLogin());
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().println("Unauthorized");
                }
            } catch (IOException | DBExeption e) {
                System.out.println("SignInServlet.doPost - error: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            System.out.println("SignInServlet.doPost - login or pass is empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
