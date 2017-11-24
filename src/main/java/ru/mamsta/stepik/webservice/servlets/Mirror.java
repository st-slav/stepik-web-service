package ru.mamsta.stepik.webservice.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Mirror extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().println(request.getParameter("key"));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
