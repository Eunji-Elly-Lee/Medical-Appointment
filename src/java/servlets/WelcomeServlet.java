package servlets;

import dataaccess.AdministratorDB;
import dataaccess.DoctorDB;
import dataaccess.PatientDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.Account;
import models.Administrator;
import models.Doctor;
import models.Patient;
import service.AccountService;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class WelcomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        allPasswordEncrypted(request, response);        
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
                    request.setAttribute("login", "login");
                    
                    if (account.getProfile().equals("DOCTOR")) {
                        DoctorDB doctorDB = new DoctorDB();
                        Doctor doctor = doctorDB.get(account.getAccount_id());
                        request.setAttribute("user", doctor);
                    } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                        AdministratorDB administratorDB = new AdministratorDB();
                        Administrator administrator = administratorDB.get(account.getAccount_id());
                        request.setAttribute("user", administrator);
                    } else if (account.getProfile().equals("PATIENT")) {
                        PatientDB patientDB = new PatientDB();
                        Patient patient = patientDB.get(account.getAccount_id());
                        request.setAttribute("user", patient);
                    }
                } catch(Exception ex) {
                    Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }        
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
        return;        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
    
    public void allPasswordEncrypted(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService accountService = new AccountService();
        
        try {
            List<Account> accounts = accountService.getAll();
            
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                
                if (account.getSalt() == null) {                    
                    accountService.update(account.getAccount_id(), account.getUser_name(),
                            account.getPassword(), account.getProfile());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
