package ru.mamsta.stepik.webservice;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.mamsta.stepik.webservice.servlets.Mirror;

public class Main {

    public static void main(String[] args) throws Exception {
        Mirror mirror = new Mirror();

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(mirror), "/mirror");
        
		server.start();
		System.out.println("Server started");
        server.join();
    }
}
