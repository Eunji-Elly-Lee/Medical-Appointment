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
        } catch(Exception ex) {
            Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/doctorProfile.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String phone_number = request.getParameter("phone_number");
        String alter_Phone_number = request.getParameter("alter_Phone_number");
        String email = request.getParameter("email");
        String pref_contact_type = request.getParameter("pref_contact_type");
        String street_address = request.getParameter("street_address");
        String postal_code = request.getParameter("postal_code");
        
        AccountService accountService = new AccountService();
        DoctorService doctorService = new DoctorService();
        
        try {
            Account account = accountService.get(user_name);
            Doctor doctor = doctorService.get(account.getAccount_id());
            
            if(password != null && !password.equals("") && password.equals(repassword)) {
                accountService.update(account.getAccount_id(), user_name, password, account.getProfile()); 
            }
            
            doctorService.update(doctor.getDoctor_id(), doctor.getFirst_name(), doctor.getLast_name(), email,
                    phone_number, alter_Phone_number, pref_contact_type, doctor.getAccount_id(), doctor.getGender(),
                    doctor.getBirth_date(), street_address, doctor.getCity(), doctor.getProvince(), postal_code);
            
            Account updatedAccount = accountService.get(user_name);
            Doctor updatedDoctor = doctorService.get(account.getAccount_id());
            
            request.setAttribute("account", updatedAccount);
            request.setAttribute("doctor", updatedDoctor);
        } catch(Exception ex) {
            Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/doctorProfile.jsp").forward(request, response);
    }
}
