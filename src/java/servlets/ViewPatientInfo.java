package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ViewPatientInfo extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       getServletContext().getRequestDispatcher("/WEB-INF/ViewPatientInfo.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
