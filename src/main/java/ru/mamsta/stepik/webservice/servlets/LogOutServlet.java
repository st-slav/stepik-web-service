package ru.mamsta.stepik.webservice.servlets;

import ru.mamsta.stepik.webservice.model.AccountService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutServlet extends HttpServlet {

    private AccountService accountService;

    public LogOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String id = null;
        for(Cookie c : request.getCookies()) {
            if (c.getName().equals("id")) {
                id = c.getValue();
                break;
            }
        }

        if(id != null && !id.equals("")) {
            accountService.unautorisation(id);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
