package servlets;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class ProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");
        
        if (user_name == null || user_name.equals("")) {
            response.sendRedirect("welcome");
            return;
        } else {
            displayInformation(request, user_name);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        return;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String phone_number = request.getParameter("phone_number");
        String alt_phone = request.getParameter("alt_phone");
        String email = request.getParameter("email");
        String pref_contact_type = request.getParameter("pref_contact_type");
        String gender = request.getParameter("gender");
        String birth_date = request.getParameter("birth_date");
        String street_address = request.getParameter("street_address");
        String city = request.getParameter("city");
        String postal_code = request.getParameter("postal_code");
        String province = request.getParameter("province");
        
        if (first_name == null || first_name.equals("") || last_name == null || last_name.equals("") ||
                phone_number == null || phone_number.equals("") || email == null || email.equals("") ||
                birth_date == null || birth_date.equals("") || street_address == null || street_address.equals("") ||
                city == null || city.equals("") || postal_code == null || postal_code.equals("") ||
                province == null || province.equals("")) {
            displayInformation(request, user_name);
            request.setAttribute("message", "Please fill out all the required information.");        
        } else {
            AccountService accountService = new AccountService();
        
            try {
                Account account = accountService.get(user_name);

                if (password != null && !password.equals("") && password.equals(repassword)) {
                    accountService.update(account.getAccount_id(), user_name, password, account.getProfile()); 
                }

                if (account.getProfile().equals("DOCTOR")) {
                    DoctorService doctorService = new DoctorService();
                    Doctor doctor = doctorService.get(account.getAccount_id());
                    doctorService.update(doctor.getDoctor_id(), first_name, last_name, email,
                        phone_number, alt_phone, pref_contact_type, doctor.getAccount_id(), gender,
                        birth_date, street_address, city, province, postal_code);
                    
                    Doctor updatedDoctor = doctorService.get(account.getAccount_id());
                    request.setAttribute("user", updatedDoctor);
                } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                    AdministratorService administratorService = new AdministratorService();
                    Administrator administrator = administratorService.get(account.getAccount_id());
                    administratorService.update(administrator.getAdmin_id(), first_name, last_name, email,
                        phone_number, alt_phone, pref_contact_type, administrator.getAccount_id(), gender,
                        birth_date, street_address, city, province, postal_code);
                    
                    Administrator updatedAdministrator = administratorService.get(account.getAccount_id());
                    request.setAttribute("user", updatedAdministrator);
                } else if (account.getProfile().equals("PATIENT")) {
                    PatientService patientService = new PatientService();
                    Patient patient = patientService.get(account.getAccount_id());
                    patientService.update(patient.getPatient_id(), patient.getHealthcare_id(), first_name, last_name, email,
                        phone_number, alt_phone, pref_contact_type, patient.getDoctor_id(), patient.getAccount_id(), gender,
                        birth_date, street_address, city, province, postal_code);
                    
                    Patient updatedPatient = patientService.get(account.getAccount_id());
                    request.setAttribute("user", updatedPatient);
                }

                Account updatedAccount = accountService.get(user_name);
                request.setAttribute("account", updatedAccount);
            } catch (Exception ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        return;
    }

    private void displayInformation(HttpServletRequest request, String user_name) {
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
