package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        // add 
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate beginDate;

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
            DoctorService doctorService = new DoctorService();
            PatientService patientService = new PatientService();
            AppointmentService as = new AppointmentService();
            
            LocalDate today = LocalDate.now();
            
            try {
                Account account = accountService.get(user_name);
                request.setAttribute("account", account);
                
                List<Appointment> appointments = as.getAllFutures(today + "");
                
                if (account.getProfile().equals("DOCTOR")) {                    
                    Doctor doctor = doctorService.get(account.getAccount_id());
                    request.setAttribute("user", doctor);
                    
//                    List<Appointment> appointments = as.getByDoctorID(doctor.getDoctor_id());
                    ArrayList<Appointment> arrlAppointments = new ArrayList();

                    for (int i = 0; i < appointments.size(); i++) {
//                        //add Jihoon
//                        beginDate = LocalDate.parse(appointments.get(i).getStart_date_time().substring(0, 10), df);
//                        
//                        if (beginDate.isAfter(LocalDate.now())) {
//                            arrlAppointments.add(appointments.get(i));
//                        }
//                        //end jihoon
                        if (appointments.get(i).getDoctor_id() == doctor.getDoctor_id()) {
                            arrlAppointments.add(appointments.get(i));
                        }
                    }

                    request.setAttribute("arrlAppointments", arrlAppointments);
                    
                    ArrayList<Patient> arrlPatients = new ArrayList();

                    for (int i = 0; i < arrlAppointments.size(); i++) {
                        Patient patient = patientService.getByPatientId(arrlAppointments.get(i).getPatient_id());
                        arrlPatients.add(patient);
                    }

                    request.setAttribute("arrlPatients", arrlPatients);
                    
//                    ArrayList<String> arrType = new ArrayList();
//                    ArrayList<Integer> arrPatientID = new ArrayList();
//                    ArrayList<String> arrTimeOnly = new ArrayList();
//                    ArrayList<String> arrTime = new ArrayList();
//
//                    for (int i = 0; i < appointments.size(); i++) {
//                        arrType.add(appointments.get(i).getType_toString(i));
//                        arrTime.add(appointments.get(i).getStart_date_time());
//                        arrPatientID.add(appointments.get(i).getPatient_id());
//                    }
//
//                    for (int i = 0; i < arrTime.size(); i++) {
//                        String[] parts = arrTime.get(i).split(" ");
//                        arrTimeOnly.add(parts[1]);
//                    }
//
//                    request.setAttribute("arrTime", arrTime);
//                    request.setAttribute("arrTimeOnly", arrTimeOnly);
//                    request.setAttribute("arrType", arrType);
//                    request.setAttribute("arrPatientID", arrPatientID);
//                    request.setAttribute("doctor_id", doctor.getDoctor_id());
                } else if (account.getProfile().equals("ADMIN")) {
                    AdministratorService administratorService = new AdministratorService();
                    Administrator administrator = administratorService.get(account.getAccount_id());
                    request.setAttribute("user", administrator);
                   
//                    List<Appointment> appointments = as.getAll();
                    ArrayList<Patient> arrlPatients = new ArrayList();
                    ArrayList<Doctor> arrlDoctors = new ArrayList();
                    ArrayList<Appointment> arrlAppointments = (ArrayList<Appointment>) appointments;
                      
//                    for (int i = 0; i < appointments.size(); i++) {
//                        //add jihoon
//                        beginDate = LocalDate.parse(appointments.get(i).getStart_date_time().substring(0, 10), df);
//                          
//                        if (beginDate.isAfter(LocalDate.now())) {
//                            arrlAppointments.add(appointments.get(i));
//                        }
//                        //end jihoon
//                    }

                    request.setAttribute("arrlAppointments", arrlAppointments);
                        
                    for (int i = 0; i < arrlAppointments.size(); i++) {
                        Patient patient = patientService.getByPatientId(arrlAppointments.get(i).getPatient_id());
                        Doctor doctor = doctorService.getByDoctorID(arrlAppointments.get(i).getDoctor_id());
                        arrlPatients.add(patient);
                        arrlDoctors.add(doctor);
                    }
                        
                    request.setAttribute("arrlPatients", arrlPatients);
                    request.setAttribute("arrlDoctors", arrlDoctors);
//                    ArrayList<String> arrType = new ArrayList();
//                    ArrayList<Integer> arrPatientID = new ArrayList();
//                    ArrayList<Integer> arrDoctorID = new ArrayList();
//                    ArrayList<String> arrTime = new ArrayList();
//                    ArrayList<String> arrTimeOnly = new ArrayList();
//
//                    for (int i = 0; i < appointments.size(); i++) {
//                        arrType.add(appointments.get(i).getType_toString(i));
//                        arrTime.add(appointments.get(i).getStart_date_time()); //added
//                        arrPatientID.add(appointments.get(i).getPatient_id());
//                        arrDoctorID.add(appointments.get(i).getDoctor_id());
//                    }
//
//                    for (int i = 0; i < arrTime.size(); i++) { //added
//                        String[] parts = arrTime.get(i).split(" ");
//                        arrTimeOnly.add(parts[1]);
//                    }
//
//                    request.setAttribute("arrTimeOnly", arrTimeOnly);//added
//                    request.setAttribute("arrTime", arrTime); //added
//                    request.setAttribute("arrType", arrType);
//                    request.setAttribute("arrPatientID", arrPatientID);
//                    request.setAttribute("arrDoctorID", arrDoctorID);
                } else if (account.getProfile().equals("PATIENT")) {
                    //add jihoon
                    request.setAttribute("step", 1);
                    //end jihoon
                    
                    Patient patient = patientService.get(account.getAccount_id());
                    request.setAttribute("user", patient);
                    
                    List<Appointment> allAppointments = as.getByPatientID(patient.getPatient_id());
                    //change type    jihoon
                    ArrayList<Appointment> futuerAppointments = new ArrayList();
//                    ArrayList<String> arrType = new ArrayList();
//                    // add  jihoon
                    ArrayList<Appointment> pastAppointments = new ArrayList();

                    for (int i = 0; i < allAppointments.size(); i++) {
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate beginDate = LocalDate.parse(allAppointments.get(i).getStart_date_time().substring(0, 10), df);
                         
                        if (beginDate.isBefore(today)) {
                            //jihoon  for history
                            pastAppointments.add(allAppointments.get(i));
                        } else {
                            futuerAppointments.add(allAppointments.get(i));
                        }
                    }
                    //end  jihoon
                        
//                    for (int i = 0; i < futuerAppointment.size(); i++) {
//                        arrType.add(futuerAppointment.get(i).getType_toString(i));
//                    }

//                    request.setAttribute("arrType", arrType);
                    request.setAttribute("pastAppointments", pastAppointments);
                    request.setAttribute("futuerAppointments", futuerAppointments);

//                    ArrayList<String> arrTime = new ArrayList();
//                    ArrayList<String> arrTimeOnly = new ArrayList();
//
//                    for (int i = 0; i < futuerAppointment.size(); i++) {
//                        arrTime.add(futuerAppointment.get(i).getStart_date_time());
//                    }
//
//                    for (int i = 0; i < arrTime.size(); i++) {
//                        String[] parts = arrTime.get(i).split(" ");
//                        arrTimeOnly.add(parts[1]);
//                    }
//
//                    request.setAttribute("arrTimeOnly", arrTimeOnly);
//                    request.setAttribute("arrTime", arrTime);
//                    
                    Doctor doctor = doctorService.getByDoctorID(patient.getDoctor_id());
                    request.setAttribute("doctorName", doctor.getFirst_name() + " " + doctor.getLast_name());
//                    request.setAttribute("doctorLastName", doctor.getLast_name());
//                    request.setAttribute("doctor_id", patient.getDoctor_id());
//                    request.setAttribute("patient_id", patient.getPatient_id());
                } else {
                    response.sendRedirect("welcome");
                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.sendRedirect("welcome");
            return;
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
            //add jihoon
            String action = request.getParameter("action");          
            boolean active = false;
            
            switch (action) {
                case "history_appointment":                  
//                    request.setAttribute("account",account );                   
//                    patient =ps.get(account.getAccount_id());
//                    DoctorService ds = new DoctorService();
//                    doctor =  ds.getByDoctorID(patient.getDoctor_id());
//                    request.setAttribute("user",patient );
//                    request.setAttribute("doctor",doctor );      
//                    
//                    AppointmentService appoinmentService = new AppointmentService();
//                    List<Appointment> appointments = appoinmentService.getByPatientID(patient.getPatient_id());     
//                    ArrayList<Appointment> pastAppointments = new ArrayList();
//                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                    LocalDate beginDate;
//                    
//                    for (int i = 0; i < appointments.size(); i++) {
//                        beginDate = LocalDate.parse(appointments.get(i).getStart_date_time().substring(0, 10), df);
//                        
//                        if (beginDate.isBefore(LocalDate.now())) {                          
//                            pastAppointments.add(appointments.get(i));                     
//                        } 
//                    }        
//                    
//                    request.setAttribute("pastAppointments", pastAppointments);
                    request.setAttribute("step", "0");
                    active =true;           
                    break;                
            }
            
            if (active){
//                getServletContext().getRequestDispatcher("/WEB-INF/viewAppointment.jsp").forward(request, response);    
                response.sendRedirect("view_appointment");
                return; 
            }      
            //end jihoon
           
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
