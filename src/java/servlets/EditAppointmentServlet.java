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
public class EditAppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");
        Appointment appointSession = (Appointment) session.getAttribute("appointmentSessionObj");
        
        AccountService as = new AccountService();
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        
        if (appointSession == null) {
            response.sendRedirect("welcome");
            return;
        } else {
            try {
                Account account = as.get(user_name);
                request.setAttribute("account", account);
                
                if (account.getProfile().equals("DOCTOR")) {                    
                    Doctor doctor = doctorService.get(account.getAccount_id());
                    request.setAttribute("user", doctor);
                } else if (account.getProfile().equals("ADMIN")) {
                    AdministratorService administratorService = new AdministratorService();
                    Administrator administrator = administratorService.get(account.getAccount_id());
                    request.setAttribute("user", administrator);
                } else {
                    Patient patient = patientService.get(account.getAccount_id());
                    request.setAttribute("user", patient);
                }
                
                String doctor = doctorService.getByDoctorID(appointSession.getDoctor_id()).getLast_name();
                String patient = patientService.getByPatientId(appointSession.getPatient_id()).getFirst_name() +
                        " " + patientService.getByPatientId(appointSession.getPatient_id()).getLast_name();
                
                request.setAttribute("date", appointSession.getStart_date_time().substring(0, 16));
                request.setAttribute("doctor", doctor);
                request.setAttribute("patient", patient);
                request.setAttribute("type", appointSession.getType());
                request.setAttribute("reason", appointSession.getReason());
            } catch (Exception ex) {
                Logger.getLogger(EditAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            getServletContext().getRequestDispatcher("/WEB-INF/editAppointment.jsp").forward(request, response);
            return;
        }        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        DoctorService d = new DoctorService();

        AppointmentService a = new AppointmentService();
        String date = request.getParameter("dateonly");
        String time = request.getParameter("timeonly");
        String dID = request.getParameter("doctor_id");

        String Datetime = date + ' ' + time;
        int typeInt = 0;
        int type = 0;
        int duration = 0;
        int doctorID = 0;
        
        try {
            type = Integer.parseInt(request.getParameter("form-select"));

        } catch (NumberFormatException e) {
        }

        String reason = request.getParameter("book_app_reason");

        if (type == 1) {
            duration = 15;
        } else if (type == 2) {
            duration = 45;
        } else if (type == 3) {
            duration = 45;
        } else if (type == 4) {
            duration = 30;
        }
        
        try {
            String user_name = (String) session.getAttribute("user_name");

            AccountService as = new AccountService();
            Account account = as.get(user_name);

            Appointment appointSession = (Appointment) session.getAttribute("appointmentSessionObj");
            Doctor doctorSession = (Doctor) session.getAttribute("doctorSessionObj");
            Patient patientSession = (Patient) session.getAttribute("patientSessionObj");
            Appointment appointment = null;
            String startdate = null;

            startdate = appointSession.getStart_date_time();
            appointment = (Appointment) a.getByDate(startdate);
            int docID = appointSession.getDoctor_id();

            //      Doctor doctor = (Doctor) d.getByDoctorID(doctorID);
            a.update(docID, startdate, appointment.getPatient_id(), duration, type, reason, appointment.getPatient_attended());

            Appointment updated =
                    new Appointment(docID, startdate, appointment.getPatient_id(), duration, type, reason, appointment.getPatient_attended());
            request.setAttribute("message", "Successfully edited");
            request.setAttribute("appointment", updated);
            request.setAttribute("doctor", doctorSession);
            request.setAttribute("patient", patientSession);

            if (account.getProfile().equals("PATIENT")) {
                getServletContext().getRequestDispatcher("/WEB-INF/editAppointmentPatient.jsp").forward(request, response);
                return;
            } else if (account.getProfile().equals("ADMIN")) {
                getServletContext().getRequestDispatcher("/WEB-INF/editAppointmentAdmin.jsp").forward(request, response);
                return;
            } else if (account.getProfile().equals("DOCTOR")) {
                getServletContext().getRequestDispatcher("/WEB-INF/editAppointmentDoctor.jsp").forward(request, response);
                return;
            }

            //  response.sendRedirect("viewAppointmentPatient");
        } catch (Exception ex) {
            Logger.getLogger(EditAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
