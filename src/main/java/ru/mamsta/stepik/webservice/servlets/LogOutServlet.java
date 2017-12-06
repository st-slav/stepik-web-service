package ru.mamsta.stepik.webservice.servlets;

import ru.mamsta.stepik.webservice.accountservice.AccountService;
import ru.mamsta.stepik.webservice.dbservice.DBService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutServlet extends HttpServlet {

    private final DBService db;
    private AccountService accountService;

    public LogOutServlet(AccountService accountService, DBService db) {
        this.db = db;
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String id = null;
        if (request.getCookies() != null && request.getCookies().length > 0) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("id")) {
                    id = c.getValue();
                    break;
                }
            }

            if (id != null && !id.equals("")) {
                accountService.unautorisation(id);
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
