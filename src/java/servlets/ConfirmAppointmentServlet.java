package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class ConfirmAppointmentServlet extends HttpServlet {
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");
        
        if (user_name == null || user_name.equals("")) {
            response.sendRedirect("welcome");
            return;
        } else {
            AccountService accountService = new AccountService();
            
            try {
                Account account = accountService.get(user_name);
                request.setAttribute("account", account);                
                
                if (account.getProfile().equals("ADMIN")) {
                    AdministratorService administratorService = new AdministratorService();
                    Administrator administrator = administratorService.get(account.getAccount_id());
                    request.setAttribute("user", administrator);                    
                } else {
                    response.sendRedirect("welcome");
                    return;
                }
            } catch (Exception ex) {
                    Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getTodayAppointments(request, response);
        getServletContext().getRequestDispatcher("/WEB-INF/confirmAppointment.jsp").forward(request, response);
        return;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
    }

    private void getTodayAppointments(HttpServletRequest request, HttpServletResponse response) {
        LocalDate tomorrow = LocalDate.now();
        // for test version!
        tomorrow = tomorrow.withMonth(1);        
        
        try {
            AppointmentService appointmentService = new AppointmentService();
            List<Appointment> appointments = appointmentService.getAllByDate(tomorrow);
            
            request.setAttribute("appointments", appointments);
        } catch (Exception ex) {
            Logger.getLogger(ConfirmAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
