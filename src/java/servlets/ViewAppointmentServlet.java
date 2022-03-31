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
public class ViewAppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");

        if (request.getParameter("logout") != null) {
            session.invalidate();
            session = request.getSession();
        } else {
            if (request.getParameter("delete") != null) {
                String time = request.getParameter("delete");
                int doctor_id = Integer.parseInt(request.getParameter("doctor_id"));
                int patient_id = Integer.parseInt(request.getParameter("patient_id"));

                AppointmentService as = new AppointmentService();
                
                try {
                    as.delete(time, doctor_id, patient_id);
                } catch (Exception ex) {
                    Logger.getLogger(ViewAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (user_name != null && !user_name.equals("")) {
                AccountService accountService = new AccountService();

                try {
                    Account account = accountService.get(user_name);
                    request.setAttribute("account", account);

                    if (account.getProfile().equals("DOCTOR")) {
                        DoctorService doctorService = new DoctorService();
                        Doctor doctor = doctorService.get(account.getAccount_id());
                        request.setAttribute("user", doctor);
                        AppointmentService as = new AppointmentService();
                        List<Appointment> appointments = as.getByDoctorID(doctor.getDoctor_id());
                        ArrayList<Appointment> arrlAppointments = new ArrayList();

                        for (int i = 0; i < appointments.size(); i++) {
                            arrlAppointments.add(appointments.get(i));
                        }

                        request.setAttribute("arrlAppointments", arrlAppointments);
                        PatientService patientService = new PatientService();
                        ArrayList<Patient> arrlPatients = new ArrayList();

                        for (int i = 0; i < appointments.size(); i++) {
                            Patient patient = patientService.getByPatientId(appointments.get(i).getPatient_id());
                            arrlPatients.add(patient);
                        }

                        request.setAttribute("arrlPatients", arrlPatients);
                        ArrayList<String> arrType = new ArrayList();
                        ArrayList<Integer> arrPatientID = new ArrayList();
                        ArrayList<String> arrTimeOnly = new ArrayList();
                        ArrayList<String> arrTime = new ArrayList();

                        for (int i = 0; i < appointments.size(); i++) {
                            arrType.add(appointments.get(i).getType_toString(i));
                            arrTime.add(appointments.get(i).getStart_date_time()); //added
                            arrPatientID.add(appointments.get(i).getPatient_id());
                        }

                        for (int i = 0; i < arrTime.size(); i++) { //added
                            String[] parts = arrTime.get(i).split(" ");
                            arrTimeOnly.add(parts[1]);
                        }

                        request.setAttribute("arrTime", arrTime); //added
                        request.setAttribute("arrTimeOnly", arrTimeOnly);//added
                        request.setAttribute("arrType", arrType);
                        request.setAttribute("arrPatientID", arrPatientID);
                        request.setAttribute("doctor_id", doctor.getDoctor_id());
                    } else if (account.getProfile().equals("ADMIN") || account.getProfile().equals("SYSADMIN")) {
                        AdministratorService administratorService = new AdministratorService();
                        Administrator administrator = administratorService.get(account.getAccount_id());
                        request.setAttribute("user", administrator);
                        AppointmentService as = new AppointmentService();
                        List<Appointment> appointments = as.getAll();
                        ArrayList<Patient> arrlPatients = new ArrayList();
                        ArrayList<Appointment> arrlAppointments = new ArrayList();
                        PatientService patientService = new PatientService();
                        
                        for (int i = 0; i < appointments.size(); i++) {
                            arrlAppointments.add(appointments.get(i));
                        }

                        request.setAttribute("arrlAppointments", arrlAppointments);
                        
                        for (int i = 0; i < appointments.size(); i++) {
                            Patient patient = patientService.getByPatientId(appointments.get(i).getPatient_id());
                            arrlPatients.add(patient);
                        }
                        
                        request.setAttribute("arrlPatients", arrlPatients);
                        ArrayList<String> arrType = new ArrayList();
                        ArrayList<Integer> arrPatientID = new ArrayList();
                        ArrayList<Integer> arrDoctorID = new ArrayList();
                        ArrayList<String> arrTime = new ArrayList();
                        ArrayList<String> arrTimeOnly = new ArrayList();

                        for (int i = 0; i < appointments.size(); i++) {
                            arrType.add(appointments.get(i).getType_toString(i));
                            arrTime.add(appointments.get(i).getStart_date_time()); //added
                            arrPatientID.add(appointments.get(i).getPatient_id());
                            arrDoctorID.add(appointments.get(i).getDoctor_id());
                        }

                        for (int i = 0; i < arrTime.size(); i++) { //added
                            String[] parts = arrTime.get(i).split(" ");
                            arrTimeOnly.add(parts[1]);
                        }

                        request.setAttribute("arrTimeOnly", arrTimeOnly);//added
                        request.setAttribute("arrTime", arrTime); //added
                        request.setAttribute("arrType", arrType);
                        request.setAttribute("arrPatientID", arrPatientID);
                        request.setAttribute("arrDoctorID", arrDoctorID);
                    } else if (account.getProfile().equals("PATIENT")) {
                        PatientService patientService = new PatientService();
                        Patient patient = patientService.get(account.getAccount_id());
                        request.setAttribute("user", patient);
                        AppointmentService as = new AppointmentService();
                        List<Appointment> appointments = as.getByPatientID(patient.getPatient_id());
                        ArrayList<String> arrType = new ArrayList();

                        for (int i = 0; i < appointments.size(); i++) {
                            arrType.add(appointments.get(i).getType_toString(i));
                        }

                        request.setAttribute("arrType", arrType);

                        ArrayList<String> arrTime = new ArrayList();
                        ArrayList<String> arrTimeOnly = new ArrayList();

                        for (int i = 0; i < appointments.size(); i++) {
                            arrTime.add(appointments.get(i).getStart_date_time());
                        }

                        for (int i = 0; i < arrTime.size(); i++) {
                            String[] parts = arrTime.get(i).split(" ");
                            arrTimeOnly.add(parts[1]);
                        }

                        request.setAttribute("arrTimeOnly", arrTimeOnly);
                        request.setAttribute("arrTime", arrTime);
                        DoctorService ds = new DoctorService();
                        Doctor doctor = ds.getByDoctorID(patient.getDoctor_id());
                        request.setAttribute("doctorFirstName", doctor.getFirst_name());
                        request.setAttribute("doctorLastName", doctor.getLast_name());
                        request.setAttribute("doctor_id", patient.getDoctor_id());
                        request.setAttribute("patient_id", patient.getPatient_id());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/viewAppointment.jsp").forward(request, response);
        return;
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
        String pID = request.getParameter("patient_id");

        String Datetime = date + ' ' + time;
        int typeInt = 0;
        int type = 0;
        int duration = 0;
        int doctorID = 0;
        int patientID = 0;
        
        try {
            doctorID = Integer.parseInt(dID);
            patientID = Integer.parseInt(pID);
            type = Integer.parseInt(request.getParameter("form-select"));
            
            if (type == 1) {
                duration = 15;
            } else if (type == 2) {
                duration = 45;
            }
        } catch (NumberFormatException e) {
            //something went wrong
        }

        String reason = request.getParameter("book_app_reason");
        String DatetimeAdmin = request.getParameter("start_date_admin");

        try {
            String user_name = (String) session.getAttribute("user_name");

            AccountService as = new AccountService();
            Account account = as.get(user_name);
            PatientService ps = new PatientService();

            Patient patient = (Patient) ps.getByPatientId(patientID);
            Appointment appointment = (Appointment) a.getByDate(Datetime);
            //     Appointment appointmentAdmin = (Appointment) a.getByDate(Datetime);
            Doctor doctor = (Doctor) d.getByDoctorID(doctorID);

            request.setAttribute("appointment", appointment);
            request.setAttribute("doctor", doctor);
            request.setAttribute("patient", patient);
            session.setAttribute("appointmentSessionObj", appointment);
            //           session.setAttribute("appointmentSessionObjAdmin", appointmentAdmin);
            session.setAttribute("doctorSessionObj", doctor);
            session.setAttribute("patientSessionObj", patient);

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
        } catch (Exception ex) {
            Logger.getLogger(EditAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
