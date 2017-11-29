package ru.mamsta.stepik.webservice.servlets;

import ru.mamsta.stepik.webservice.model.AccountService;
import ru.mamsta.stepik.webservice.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        final User requestUser = new User(request.getParameter("login"), request.getParameter("password"));
        final User user = accountService.getUser(requestUser.getLogin());
        try {
            if (user != null && user.getPassword().equals(requestUser.getPassword())) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.addCookie(new Cookie("id", accountService.autorization(user)));
                response.getWriter().println("Authorized: " + user.getLogin());
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("Unauthorized");
            }
        } catch (IOException e) {
            System.out.println("SignInServlet.doPost - error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
