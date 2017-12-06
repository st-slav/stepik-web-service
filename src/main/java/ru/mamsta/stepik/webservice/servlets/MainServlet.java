package ru.mamsta.stepik.webservice.servlets;

import ru.mamsta.stepik.webservice.accountservice.AccountService;
import ru.mamsta.stepik.webservice.dbservice.DBService;
import ru.mamsta.stepik.webservice.dbservice.dataset.UserDataSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    private final AccountService accountService;
    private final DBService db;

    public MainServlet(AccountService accountService, DBService db) {
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
                UserDataSet user = accountService.getAutorizationUser(id);
                if (user != null) {
                    try {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().println("Hello, " + user.getLogin());
                        return;
                    } catch (IOException e) {
                        System.out.println("MainServlet doGet: " + e.getMessage());
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        return;
                    }
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
