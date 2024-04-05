## Medical Clinic Appointment Web Application

![main page](/web/img/main_page.png)
![booking page](/web/img/booking_page.png)
![staff page](/web/img/staff_page.png)

This web application empowers patients of Surpass Health Clinic to conveniently schedule appointments online, eliminating the need for phone calls or clinic visits. Patients can book appointments anytime, anywhere, streamlining the appointment scheduling process.

### User Roles and Access Control

The application implements a robust role-based access control system, enabling distinct functionalities for various user types:

- **Patient**: Books appointments with their doctor at available times, fostering increased autonomy and flexibility
- **Doctor**: Manages their schedule by registering available appointment slots aligned with their shifts and has the ability to directly schedule appointments with their patients
- **Administrator**: Facilitates appointment scheduling on behalf of patients and doctors, ensuring smooth clinic operations and manages patient attendance records
- **System Administrator**: Possesses comprehensive control, overseeing staff management, data maintenance, and system security

### Features

- **Welcoming Interface**: A calming atmosphere is created through a light blue and green color scheme, fostering a sense of ease and trust for patients scheduling appointments.
- **Responsive Design**: The application seamlessly adapts to any device, ensuring a consistent and optimal user experience across desktop computers, tablets, and smartphones.
- **Secured Access**: Granular access controls prevent unauthorized actions. Users can only access functionalities aligned with their designated role.
- **Enhanced Security**: High-level security safeguards user data through robust encryption with AES, SHA-256, and Salt, protecting patient privacy and clinic information.

### Technologies Used

The application was created using NetBeans, and it leverages the following technologies:

**Front-End**:

- HTML, and CSS: Used for creating the user interface and ensuring a visually appealing design

**Back-End**:

- Java Servlet and JSP: Used for handling server-side logic and generating dynamic content
- JavaBeans and JSTL: Manage data and simplify JSP page development
- JavaMail: Integrates email functionality for notifications and communication
- Model-View-Controller (MVC) Pattern and Filter: Adopted for organizing the application's structure and implementing pre-processing logic
- Encryption (AES, SHA-256, and Salt): Implements multi-layered encryption to safeguard sensitive data
- MySQL and JDBC Driver: Utilized for interacting with the database, managing data, and executing SQL queries

<br />

This readme file provides a comprehensive overview of the Medical Clinic Appointment web application. For further details or inquiries, feel free to reach out!
