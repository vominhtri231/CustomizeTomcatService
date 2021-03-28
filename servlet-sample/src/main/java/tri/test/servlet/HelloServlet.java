package tri.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tri.test.api.time.TimeProvider;

@Singleton
public class HelloServlet extends HttpServlet {

    @Inject
    private TimeProvider timeProvider;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        pw.println("<html><body>");
        pw.println("Welcome to servlet on " + timeProvider.getCurrentDate());
        pw.println("</body></html>");
        pw.close();
    }
}
