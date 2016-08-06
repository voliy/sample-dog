package voliy.samples.dog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String dogName = req.getParameter("name");
        if (dogName == null) {
            dogName = "Not defined";
        }
        resp.getWriter().println("<h1>" + dogName + "</h1>");
    }
}
