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
public class ViewStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");
        String complete = (String) session.getAttribute("complete");

        getAllStaff(request, response);

        if (complete != null) {
            request.setAttribute("message", "User information has been updated successfully.");
            session.setAttribute("complete", null);
        }

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

        getServletContext().getRequestDispatcher("/WEB-INF/viewStaff.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        DoctorService ds = new DoctorService();
        AdministratorService ads = new AdministratorService();

        String action = request.getParameter("action");
        String account_id = request.getParameter("account_id");
        int accountID = Integer.parseInt(account_id);

        if (account_id != null) {
            Account account = null;
            
            try {
                account = as.get(accountID);
            } catch (Exception ex) {
                Logger.getLogger(ViewStaffServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (action.equals("edit")) {
                session.setAttribute("selectedUser", account.getUser_name());
                session.setAttribute("editCheck", "editCheck");
                response.sendRedirect("profile");
                return;
            } else if (action.equals("delete")) {
                if (account.getProfile().equals("DOCTOR")) {
                    try {
                        ds.delete(account.getAccount_id());
                        as.delete(account.getUser_name());
                    } catch (Exception ex) {
                        Logger.getLogger(ViewStaffServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    response.sendRedirect("view_staff");
                    request.setAttribute("message", "User is deleted successfully.");
                    return;
                } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                    try {
                        ads.delete(account.getAccount_id());
                        as.delete(account.getUser_name());
                    } catch (Exception ex) {
                        Logger.getLogger(ViewStaffServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    response.sendRedirect("view_staff");
                    request.setAttribute("message", "User is deleted successfully.");
                    return;
                }
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/viewStaff.jsp").forward(request, response);
        return;
    }

    public void getAllStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdministratorService as = new AdministratorService();
        DoctorService ds = new DoctorService();

        List<Administrator> admins = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();

        try {
            admins = as.getAll();
            doctors = ds.getAll();
        } catch (Exception ex) {
            Logger.getLogger(ViewStaffServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("admins", admins);
        request.setAttribute("doctors", doctors);
    }
}
