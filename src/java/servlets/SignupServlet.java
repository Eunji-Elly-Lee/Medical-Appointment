package servlets;

import java.io.IOException;
import java.util.logging.*;
import java.util.regex.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");
        
        if (user_name != null && !user_name.equals("")) {
            response.sendRedirect("welcome");
            return;
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usernameEntered = request.getParameter("signup_username");
        String phoneEntered = request.getParameter("signup_phonenum");
        String phoneAltEntered = request.getParameter("signup_phonenum_alt");
        String healthCareNumber = request.getParameter("signup_healthcare_num");

        String passEntered = request.getParameter("signup_password");
        String reenterPassEntered = request.getParameter("signup_re_enter_password");

        String firstEntered = request.getParameter("signup_firstname");
        String lastEntered = request.getParameter("signup_lastname");

        String emailEntered = request.getParameter("signup_email");
        String birthEntered = request.getParameter("signup_birth_date");
        String prefferedEntered = request.getParameter("prefered_notification_radio");
        String genderEntered = request.getParameter("gender_radio");

        String streetEntered = request.getParameter("signup_address");
        String street2Entered = request.getParameter("signup_address2");
        String provinceEntered = request.getParameter("signup_state_province");
        String cityEntered = request.getParameter("signup_city");
        String postalEntered = request.getParameter("signup_postal");

        boolean checkNum = true;
        boolean checkNumAlt = true;
        boolean checkHealthCare = true;
        boolean checkPass = true;

        for (char c : phoneEntered.toCharArray()) {
            if (phoneEntered.length() != 10) {

                checkNum = false;
            } else if (!Character.isDigit(c)) {
                checkNum = false;
            }
        }

        for (char c : phoneAltEntered.toCharArray()) {
            if (phoneAltEntered.length() != 10) {
                checkNumAlt = false;

            } else if (!Character.isDigit(c)) {
                checkNumAlt = false;
            }
        }

        for (char c : healthCareNumber.toCharArray()) {
            if (healthCareNumber.length() <= 8) {
                checkHealthCare = false;
            } else if (!Character.isDigit(c)) {
                checkHealthCare = false;
            }
        }

        if (!passEntered.equals(reenterPassEntered)) {
            checkPass = false;
        }

        String regexEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n"
                + ")*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        String regexPostal = "^(?![DFIOQUWZ])[A-Z]{1}[0-9]{1}(?![DFIOQU])[A-Z]{1}[ ]{1}[0-9]{1}(?![DFIOQU])[A-Z]{1}[0-9]{1}$";
        boolean checkEmail = true;
        boolean checkPostal = true;
        if (emailEntered == null || emailEntered.equals("")) {
            request.setAttribute("emailErrorMessage", "*Email is required");
        } else {
            Pattern p = Pattern.compile(regexEmail);
            Matcher m = p.matcher(emailEntered);
            checkEmail = m.matches();
        }

        if (postalEntered == null || postalEntered.equals("")) {
            request.setAttribute("postalErrorMessage", "*Postal Code is required");
        } else {
            Pattern p2 = Pattern.compile(regexPostal);
            Matcher m2 = p2.matcher(postalEntered);
            checkPostal = m2.matches();
        }

        boolean insertInfo = true;
        if (usernameEntered == null || usernameEntered.equals("")) {
            request.setAttribute("userNameErrorMessage", "*Username is required");
            insertInfo = false;
        } else if (usernameEntered.contains("&") || usernameEntered.contains("=") ||
                usernameEntered.contains("_") || usernameEntered.contains("'") || usernameEntered.contains("-") ||
                usernameEntered.contains("+") || usernameEntered.contains(",") || usernameEntered.contains("<") ||
                usernameEntered.contains(">") || usernameEntered.contains("..")) {
            request.setAttribute("userNameErrorMessage", "*Invalid username");
            insertInfo = false;
        }

        if (phoneEntered == null || phoneEntered.equals("")) {
            request.setAttribute("phoneErrorMessage", "*Phone number is required");
            insertInfo = false;
        } else if (checkNum == false) {
            request.setAttribute("phoneErrorMessage", "*Invalid phone number ex. 4035431090");
            insertInfo = false;
        }

        if (checkNumAlt == false) {
            request.setAttribute("phoneAltErrorMessage", "*Invalid phone alt number ex. 4035431090");
            insertInfo = false;
        }

        if (healthCareNumber == null || healthCareNumber.equals("")) {
            request.setAttribute("healthErrorMessage", "*Health care number is required");
            insertInfo = false;
        } else if (checkHealthCare == false) {
            request.setAttribute("healthErrorMessage", "*Invalid health care number");
            insertInfo = false;
        }

        if (passEntered == null || passEntered.equals("") || reenterPassEntered == null || reenterPassEntered.equals("")) {
            request.setAttribute("passErrorMessage", "*Password is required");
            insertInfo = false;
        } else if (checkPass == false) {
            request.setAttribute("passErrorMessage", "*Password does not match ");
            insertInfo = false;
        }

        if (checkEmail == false) {
            request.setAttribute("emailErrorMessage", "*Invalid Email ex. example@domain.com");
            insertInfo = false;
        }

        if (checkPostal == false) {
            request.setAttribute("postalErrorMessage", "*Invalid Postal Code ex. T2L 4T7");
            insertInfo = false;
        }

//             String firstEntered = request.getParameter("signup_firstname");
//        String lastEntered = request.getParameter("signup_lastname");
//
//        String emailEntered = request.getParameter("signup_email");
//        String birthEntered = request.getParameter("signup_birth_date");
//        String prefferedEntered = request.getParameter("prefered_notification_radio");
//        String genderEntered = request.getParameter("gender_radio");
//
//        String streetEntered = request.getParameter("signup_address");
//        String street2Entered = request.getParameter("signup_address2");
//        String provinceEntered = request.getParameter("signup_state_province");
//        String cityEntered = request.getParameter("signup_city");
//        String postalEntered = request.getParameter("signup_postal");
        if (firstEntered == null || firstEntered.equals("")) {
            request.setAttribute("firstErrorMessage", "*First Name is required");
            insertInfo = false;
        }

        if (lastEntered == null || lastEntered.equals("")) {
            request.setAttribute("lastErrorMessage", "*Last Name is required");
            insertInfo = false;
        }

        if (birthEntered == null || birthEntered.equals("")) {
            request.setAttribute("birthErrorMessage", "*Birth date is required");
            insertInfo = false;
        }

        if (genderEntered == null || genderEntered.equals("")) {
            request.setAttribute("genderErrorMessage", "*Gender is required");
            insertInfo = false;
        }

        if (prefferedEntered == null || prefferedEntered.equals("")) {
            request.setAttribute("notiErrorMessage", "*Prefered Notification is required");
            insertInfo = false;
        }

        if (streetEntered == null || streetEntered.equals("")) {
            request.setAttribute("addressErrorMessage", "*Address is required");
            insertInfo = false;
        }

        if (cityEntered == null || cityEntered.equals("")) {
            request.setAttribute("cityErrorMessage", "*City is required");
            insertInfo = false;
        }

        if (provinceEntered == null || provinceEntered.equals("")) {
            request.setAttribute("provErrorMessage", "*Province is required");
            insertInfo = false;
        }

        PatientService ps = new PatientService();
        AccountService as = new AccountService();
        try {
            if (insertInfo == true) {
                as.insert(0, usernameEntered, passEntered, "PATIENT");
                Account newAccount = as.getAll().get(as.getAll().size()-1);
                
                ps.insert(0, healthCareNumber, firstEntered, lastEntered, emailEntered, phoneEntered,
                        phoneAltEntered, prefferedEntered, 1234567, newAccount.getAccount_id(), genderEntered,
                        birthEntered, streetEntered, cityEntered, provinceEntered, postalEntered);
            } else {
             Patient patient = new Patient(0,  healthCareNumber,  firstEntered, lastEntered ,  emailEntered,
                     phoneEntered , phoneAltEntered, prefferedEntered, 1234567, 4618, genderEntered,
                     birthEntered, streetEntered, cityEntered, provinceEntered, postalEntered);
                request.setAttribute("patient", patient);
        
                Account account = new Account(0, usernameEntered, passEntered);
                request.setAttribute("account", account);
            } 
        } catch (Exception ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
        return;
    }
}
