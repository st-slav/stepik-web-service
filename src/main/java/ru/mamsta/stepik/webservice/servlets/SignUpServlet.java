package ru.mamsta.stepik.webservice.servlets;

import ru.mamsta.stepik.webservice.model.AccountService;
import ru.mamsta.stepik.webservice.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        final User user = new User(request.getParameter("login").trim(), request.getParameter("password"));
        if (!accountService.containsUser(user.getLogin())) {
            accountService.registerUser(user);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                response.getWriter().println("user with such login already exists");
            } catch (IOException e) {
                System.out.println("SignUpServlet.doPost - error: " + e.getMessage());
            }
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
