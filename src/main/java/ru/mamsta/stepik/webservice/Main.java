package ru.mamsta.stepik.webservice;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.mamsta.stepik.webservice.dbservice.DBService;
import ru.mamsta.stepik.webservice.accountservice.AccountService;
import ru.mamsta.stepik.webservice.servlets.LogOutServlet;
import ru.mamsta.stepik.webservice.servlets.MainServlet;
import ru.mamsta.stepik.webservice.servlets.SignInServlet;
import ru.mamsta.stepik.webservice.servlets.SignUpServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        DBService db = new DBService();
        db.printConnectInfo();

        AccountService accountService = new AccountService();

        SignUpServlet signUpServlet = new SignUpServlet(accountService, db);
        SignInServlet signInServlet = new SignInServlet(accountService, db);
        LogOutServlet logOutServlet = new LogOutServlet(accountService, db);
        MainServlet mainServlet = new MainServlet(accountService, db);

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(signUpServlet), "/signup");
        context.addServlet(new ServletHolder(signInServlet), "/signin");
        context.addServlet(new ServletHolder(logOutServlet), "/logout");
        context.addServlet(new ServletHolder(mainServlet), "/main");

        System.out.println("Server started");
		server.start();
        server.join();
    }
}
