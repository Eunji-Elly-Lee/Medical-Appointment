package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class SignupStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
          getServletContext().getRequestDispatcher("/WEB-INF/signupStaff.jsp").forward(request, response);
          return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
