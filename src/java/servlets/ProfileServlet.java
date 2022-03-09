package servlets;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");        
        AccountService accountService = new AccountService();
        DoctorService doctorService = new DoctorService();
        
        try {
            Account account = accountService.get(user_name);
            Doctor doctor = doctorService.get(account.getAccount_id());
            
            request.setAttribute("account", account);
            request.setAttribute("doctor", doctor);
        } catch (Exception ex) {
            Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/doctorProfile.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
