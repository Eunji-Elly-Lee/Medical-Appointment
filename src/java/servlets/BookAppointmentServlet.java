package servlets;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class BookAppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");
        String appointment_date = request.getParameter("appointment_date");
        
        if (user_name == null || user_name.equals("")) {
            response.sendRedirect("welcome");
            return;
        } else {            
            AccountService accountService = new AccountService();
            DoctorService doctorService = new DoctorService();
            PatientService patientService = new PatientService();
            AvailabilityService availabilityService = new AvailabilityService();
            
            List<Availability> availabilities = new ArrayList<>();
            List<String> available_dates = new ArrayList<>();
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            // for test version!
            tomorrow = tomorrow.withMonth(1);
            
            try {
                Account account = accountService.get(user_name);
                request.setAttribute("account", account);
                
                if (appointment_date == null || appointment_date.equals("")) {
                    if (account.getProfile().equals("ADMIN")) {
                        if (request.getParameter("step") != null && request.getParameter("step").equals("1")) {
                            request.setAttribute("step", "1");
                        } else if (request.getParameter("step") != null && request.getParameter("step").equals("2")) {
                            request.setAttribute("step", "2");
                        } else {
                            request.setAttribute("step", "0");
                        }
                    } else {
                        request.setAttribute("step", "1");
                    }
                }
                
                if (account.getProfile().equals("DOCTOR")) {                    
                    Doctor doctor = doctorService.get(account.getAccount_id());
                    
                    availabilities =
                            availabilityService.getAllByDoctorDate(doctor.getDoctor_id(), tomorrow.toString());

                    if (!availabilities.isEmpty()) {
                        for (Availability availability: availabilities) {
                            available_dates.add(availability.getStart_date_time().substring(0, 11));
                        }

                        request.setAttribute("available_dates", available_dates);
                    }                  
                    
                    request.setAttribute("user", doctor);
                } else if (account.getProfile().equals("ADMIN")) {
                    AdministratorService administratorService = new AdministratorService();
                    Administrator administrator = administratorService.get(account.getAccount_id());
                    request.setAttribute("user", administrator);
                } else if (account.getProfile().equals("PATIENT")) {                    
                    Patient patient = patientService.get(account.getAccount_id());
                    
                    if (patient.getDoctor_id() == 1234567) {
                        for (int i = 0; i < 7; i++) {
                            available_dates.add(tomorrow.plusDays(i) + "");
                        }
                        
                        request.setAttribute("message",
                                "You are new! You should proceed with \"New Patient Meeting\" first.");
                    } else {
                        Doctor doctor = doctorService.getByDoctorID(patient.getDoctor_id()); 
                        
                        availabilities =
                                availabilityService.getAllByDoctorDate(doctor.getDoctor_id(), tomorrow.toString());

                        if (!availabilities.isEmpty()) {
                            for (Availability availability: availabilities) {
                                available_dates.add(availability.getStart_date_time().substring(0, 11));
                            }

                            request.setAttribute("available_dates", available_dates);
                        }
                    }
                    
                    request.setAttribute("user", patient);
                }
            } catch (Exception ex) {
                    Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/bookAppointment.jsp").forward(request, response);
        return;
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("user_name");       
        String appointment_date = request.getParameter("appointment_date");
        String action = request.getParameter("action");
        
        AccountService accountService = new AccountService();        
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService();
        AppointmentTypeService appointmentTypeService = new AppointmentTypeService();
        AvailabilityService availabilityService = new AvailabilityService();
        CalendarService calendarService = new CalendarService();
        
        List<AppointmentType> types = new ArrayList<>();
        List<Availability> availabilities = new ArrayList<>();
        List<Doctor> searched_doctors = new ArrayList<>();
        List<Patient> searched_patients = new ArrayList<>();
        
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // for test version!
        tomorrow = tomorrow.withMonth(1);
        Doctor doctor = null;
        Patient patient = null;
        
        try {
            Account account = accountService.get(user_name);
            request.setAttribute("account", account);
                
            if (account.getProfile().equals("DOCTOR")) {
                doctor = doctorService.get(account.getAccount_id());
                types.add(appointmentTypeService.get(1));
                types.add(appointmentTypeService.get(2));   
                types.add(appointmentTypeService.get(3)); 
                    
                availabilities =
                        availabilityService.getAllByDoctorDate(doctor.getDoctor_id(), tomorrow.toString());
                
                request.setAttribute("user", doctor);
            } else if (account.getProfile().equals("ADMIN")) {
                AdministratorService administratorService = new AdministratorService();
                Administrator administrator = administratorService.get(account.getAccount_id());
                request.setAttribute("user", administrator);
            } else if (account.getProfile().equals("PATIENT")) {
                patient = patientService.get(account.getAccount_id());    
                
                if (patient.getDoctor_id() == 1234567) {
                    types.add(appointmentTypeService.get(4));
                    request.setAttribute("message",
                            "You are new! You should proceed with \"New Patient Meeting\" first.");
                } else {
                    doctor = doctorService.getByDoctorID(patient.getDoctor_id());                    
                    types.add(appointmentTypeService.get(1));
                    types.add(appointmentTypeService.get(2));   
                    
                    availabilities =
                            availabilityService.getAllByDoctorDate(doctor.getDoctor_id(), tomorrow.toString());
                }
                
                request.setAttribute("user", patient);
                request.setAttribute("doctor", doctor);                
            }
            
            switch (action) {
                case "search_name":
                    String name = request.getParameter("appointment_date");
                    
                    if (name == null || name.equals("")) {
                        request.setAttribute("message", "Please enter the name.");
                        request.setAttribute("step", "0");
                    } else {
                        
                    }
                    
                    break;
                case "select_date":
                    if (appointment_date.equals("0")) {
                        request.setAttribute("message", "Please select the date for the appointment.");
                        request.setAttribute("step", "1");
                    } else {
                        request.setAttribute("step", "2");
                    }
                    
                    break;
                
                case "book_appointment":
                    int type_selection = Integer.parseInt(request.getParameter("type_selection"));
                    String time_selection = request.getParameter("time_selection");                    
                    String book_app_reason = request.getParameter("book_app_reason");
                    request.setAttribute("type_selection", type_selection);
                    request.setAttribute("time_selection", time_selection);
                    request.setAttribute("book_app_reason", book_app_reason);
                    
                    if (type_selection == 0 || time_selection.equals("0") || 
                            book_app_reason == null || book_app_reason.equals("")) {
                        request.setAttribute("message", "Please fill out all information for the appointment.");
                        request.setAttribute("step", "2");
                    } else {
                        if (account.getProfile().equals("DOCTOR")) {
                            int patient_id = Integer.parseInt(request.getParameter("patient_selection"));
                            request.setAttribute("patient_selection", patient_id);

                            if (patient_id == 0) {
                                request.setAttribute("message", "Please fill out all information for the appointment.");
                            } else {
                                appointmentService.insert(doctor.getDoctor_id(), appointment_date + " " + time_selection,
                                        patient_id, appointmentTypeService.get(type_selection).getStd_duration(),
                                        type_selection, book_app_reason, false);
                                
                                request.setAttribute("message", "Tha ppointment has been booked successfully.");
                                request.setAttribute("step", "1");
                            }
                        } else if (account.getProfile().equals("ADMIN")) {
                        
                        } else if (account.getProfile().equals("PATIENT")) {
                            appointmentService.insert(doctor.getDoctor_id(), appointment_date + " " + time_selection,
                                    patient.getPatient_id(), appointmentTypeService.get(type_selection).getStd_duration(),
                                    type_selection, book_app_reason, false);
                                
                            request.setAttribute("message", "Tha ppointment has been booked successfully.");
                            request.setAttribute("step", "1");
                        }
                    }
                    
                    break;
            }
            
            if (!availabilities.isEmpty()) {
                List<String> available_dates = new ArrayList<>();

                for (Availability availability: availabilities) {
                    available_dates.add(availability.getStart_date_time().substring(0, 11));
                }
                        
                request.setAttribute("available_dates", available_dates);
            }
            
            if (appointment_date != null && !appointment_date.equals("0")) {
                String start_date_time = null;
                String end_date_time = null;
                String duration = null;

                if (patient != null && patient.getDoctor_id() == 1234567) {
                    start_date_time = appointment_date + " 08:00:00";
                    end_date_time = appointment_date + " 17:00:00";
                } else {
                    if (account.getProfile().equals("DOCTOR")) {
                        List<Patient> patients = patientService.getAllByDoctor(doctor.getDoctor_id());
                        request.setAttribute("patients", patients);
                    }

                    for (Availability availability: availabilities) {
                        if (availability.getStart_date_time().startsWith(appointment_date)) {
                            start_date_time = availability.getStart_date_time().substring(0, 19);
                            end_date_time = start_date_time;
                            duration = availability.getDuration() + "";                            
                        }
                    }

                    LocalTime end_time = LocalTime.of(Integer.parseInt(end_date_time.substring(11, 13)),
                                        Integer.parseInt(end_date_time.substring(14, 16)),
                                        Integer.parseInt(end_date_time.substring(17)));                    
                    end_time = end_time.plusMinutes(Integer.parseInt(duration));                    
                    end_date_time = end_date_time.substring(0, 11) + end_time;
                }

                List<Calendar> calendars = calendarService.getAllAvailable(start_date_time, end_date_time);

                if (!calendars.isEmpty()) {
                    List<String> available_times = new ArrayList<>();

                    for (Calendar calendar: calendars) {
                        available_times.add(calendar.getDate_time().substring(11, 16));
                    }

                    request.setAttribute("available_times", available_times);
                }
            }
            
            request.setAttribute("types", types);
            request.setAttribute("appointment_date", appointment_date);
        } catch (Exception ex) {
                Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/bookAppointment.jsp").forward(request, response);
        return;
    }
}
