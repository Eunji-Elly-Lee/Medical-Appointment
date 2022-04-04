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
        String complete = (String) session.getAttribute("complete");
        String deleteCheck = (String) session.getAttribute("deleteCheck");
        String noName = (String) session.getAttribute("noName");

        getAllPatient(request, response);

        if (complete != null) {
            request.setAttribute("message", "User information has been updated successfully.");
            session.setAttribute("complete", null);
        } else if (deleteCheck != null) {
            request.setAttribute("message", "User is deleted successfully.");
            session.setAttribute("deleteCheck", null);
        } else if (noName != null) {
            request.setAttribute("message", "Please enter the name");
            session.setAttribute("noName", null);
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

        getServletContext().getRequestDispatcher("/WEB-INF/viewPatient.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        PatientService ps = new PatientService();
        DoctorService ds = new DoctorService();
        AdministratorService ads = new AdministratorService();

        List<Patient> searched_patients = new ArrayList<>();

        String user_name = (String) session.getAttribute("user_name");
        String name = request.getParameter("name");
        String action = request.getParameter("action");
        String account_id = request.getParameter("account_id");

        Account account = null;

        try {
            account = as.get(user_name);
        } catch (Exception ex) {
            Logger.getLogger(ViewPatientServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (account_id == null && action.equals("search_name")) {
            if (!name.equals("")) {
                if (account.getProfile().equals("DOCTOR")) {
                    Doctor doctor = null;

                    try {
                        doctor = ds.get(account.getAccount_id());
                        request.setAttribute("account", account);
                        searched_patients = ps.getAllByName(name);
                    } catch (Exception ex) {
                        Logger.getLogger(ViewPatientServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    request.setAttribute("searchedPatients", searched_patients);
                    request.setAttribute("user", doctor);
                    getAllPatient(request, response);
                    request.setAttribute("searched", true);
                } else if (account.getProfile().equals("ADMIN")) {
                    Administrator admin = null;

                    try {
                        admin = ads.get(account.getAccount_id());
                        request.setAttribute("account", account);
                        searched_patients = ps.getAllByName(name);
                    } catch (Exception ex) {
                        Logger.getLogger(ViewPatientServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    request.setAttribute("searchedPatients", searched_patients);
                    request.setAttribute("user", admin);
                    getAllPatient(request, response);
                    request.setAttribute("searched", true);
                }
            } else {
                session.setAttribute("noName", "noName");
                response.sendRedirect("view_patient");
                return;
            }
        }

        if (account_id != null) {
            int accountID = Integer.parseInt(account_id);

            try {
                account = as.get(accountID);
            } catch (Exception ex) {
                Logger.getLogger(ViewStaffServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (action.equals("edit")) {
                session.setAttribute("selectedUser", account.getUser_name());
                session.setAttribute("editCheckPatient", "editCheckPatient");
                response.sendRedirect("profile");
                return;
            } else if (action.equals("delete")) {
                try {
                    ps.delete(account.getAccount_id());
                    as.delete(account.getUser_name());
                } catch (Exception ex) {
                    Logger.getLogger(ViewStaffServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                session.setAttribute("deleteCheck", "deleteCheck");
                response.sendRedirect("view_patient");
                return;
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/viewPatient.jsp").forward(request, response);
        return;
    }

    public void getAllPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PatientService ps = new PatientService();

        List<Patient> patients = new ArrayList<>();

        try {
            patients = ps.getAll();
        } catch (Exception ex) {
            Logger.getLogger(ViewPatientServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("patients", patients);
    }
}
