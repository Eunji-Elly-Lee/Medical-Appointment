package servlets;

import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class ViewPatientServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");
        
        if (request.getParameter("logout") != null) {
            session.invalidate();
            session = request.getSession();
        } else {
            if (user_name != null && !user_name.equals("")) {
                AccountService accountService = new AccountService();

                try {
                    Account account = accountService.get(user_name);
                    request.setAttribute("account", account);
                    
                    if (account.getProfile().equals("DOCTOR")) {
                        DoctorService doctorService = new DoctorService();
                        Doctor doctor = doctorService.get(account.getAccount_id());
                        request.setAttribute("user", doctor);
                    } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                        AdministratorService administratorService = new AdministratorService();
                        Administrator administrator = administratorService.get(account.getAccount_id());
                        request.setAttribute("user", administrator);
                        PatientService patientService = new PatientService();
                        List<Patient> patients = patientService.getAll();
                        ArrayList<Patient>arrlPatients = new ArrayList();
                        
                        for (int i = 0; i < patients.size();i++) {
                            arrlPatients.add(patients.get(i));
                        }
                        
                        request.setAttribute("arrlPatients",  arrlPatients);                        
                    } else if (account.getProfile().equals("PATIENT")) {
                        PatientService patientService = new PatientService();
                        Patient patient = patientService.get(account.getAccount_id());
                        request.setAttribute("user", patient);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }        
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/viewPatient.jsp").forward(request, response);
        return;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        
        String account_id = request.getParameter("account_id");
        int accountID = Integer.parseInt(account_id);
 
        if (account_id != null) {
            Account account = null;
            
            try {
                account = as.get(accountID);
            } catch (Exception ex) {
                Logger.getLogger(ViewPatientServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            session.setAttribute("selectedUser", account.getUser_name());
            session.setAttribute("editCheck", "editCheck");
            response.sendRedirect("profile");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/viewPatient.jsp").forward(request, response);
            return;
        }
    }
}
