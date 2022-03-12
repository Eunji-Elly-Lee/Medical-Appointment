<%-- 
    Document   : Signup page
    Created on : Feb 13, 2022
    Author     : Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
                crossorigin="anonymous">
        </script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
        <style><%@ include file="/css/styleCommon.css" %></style>  
        <style><%@ include file="/css/styleSignupPage.css" %></style>      
        <title>Sign Up Page</title>
    </head>
    
    <body>
        <div>
            <div class="headers">
                <div class="fixed_position_header">
                    <div class="top_blue_div"></div>
                    <div class="topnav_wrapper">
                        <div class="topnav">
                            <div class="topnav_left">
                                <a href="welcome"><img src="img/logo.png" height="88px"></a>
                            </div>
                            <div class="topnav_right">
                                <nav>
                                    <ul>
                                        <li><a href="welcome">Home</a></li>
                                        <li>
                                            <a class="drop-down-tab" href="">
                                                Appointment <i class="bi bi-caret-down-fill"></i>
                                            </a>
                                            <ul class="subnav">
                                                <li><a href="book_appointment">Book Appointment</a></li>
                                                <li><a href="view_appointment">View Appointment</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="p-0 mobile_nav">
                    <div class="pe-2 mobile_header">
                        <a href="welcome"><img src="img/logo.png" width="105px"></a>
                        <div><span class="hello">Hello</span>
                            <img
                                src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACMAAAAjCAYAAAAe2bNZAAAABmJLR0QA/wD/
                                AP+gvaeTAAAD40lEQVRYhe2XS2xUZRTHf+eb6YOqpYD0laghdkAw7HwUEeLCqKnR0qJ1Y2JcWE3YqtWdS+
                                tKl8pCFy5IX9MRKFvAxMYmLtQgEdEGA22nY63UCNNO5/5dzEyZzsydudMUVpzVPd95/e693/
                                3OuXBXbqOcuqJtm5HHqg2IXdMez9Et0wsYEUQzUAusAHHgMrIzhIn17rRLtwVmPK6nPE+DGE8Hzi6+
                                xdlAb4tNbgpMdFFNWtFxxCuBIYqphlZrXX/fdru+YZiReUWcp2+ARypUWwIaK/hcDDl7ubvZLvs5uAogk2VA4sLeqq+
                                xrb2tbutqrTWB9QPzPv57054mY/Pq8KtZ8slEF9Wk5TIgxtXVtB3sa7c/
                                C02jM3rInL4D2n1qXqyvsc6uHbZUaCj5ZLSi474ggGTHSoEAHG23K2Z2zC8W2JtMeZ+XMhTBjMZ1sMJmTfzcwqkydlLNnAT+
                                8vew10bn1FkRxjx9XK4QMP2RmVfOoc8sDUyXcTEzDZaFiV3TngDnSNDTdntZqzg8Mq+IL4zn6A5QJDIyo7Kf+
                                vis9gEPV0rktL7eOhiZugLAEDJ9OiSFStmGpJBMnwXJg/
                                SiL4zBrkA5jOfDcY2OJtSWvx5LqD0UV1TwbCAY2F9QPwcpi8aVJNP0gkoSOA+
                                6CvYAcAioryKe6zdty5u7LAkQzi0OL9IYrg6EbOHnNtD81+SeMA1kburWa3p1G0tkxoA7Kvcm+
                                S93vQZjZjKYu8Msf3dFbDmnhPMtgj+ABwMkWUZMYfoFEcfcP8hrwmhB9ijG40BdgDw/
                                5ivrYJBNYHrGJ3AB9LU8N7a0zFRu05WSL6dV31jHE+a8o2Cv43MAyux0vr5u540ltJu0fi2IWQUNhkJusHun/
                                esH4CcTC2pMprwBsPcpuPmQs0j+
                                fFP0GYzNeucxDmXVlMm6e9rsTLUQhTIyqy5nGgdqskvnelvdurdQ3CixdwHldM9RNHdsRMLGQl5eYfZhoU8RTE+
                                bTYGGs2qNScOFDa1aGUtot4eirJ1jOlFqSC85XK3Wun7gYlZtc57Ojs/pyQ2BxHWAtM4CudZxob7GvVPK1/fojM2rI52Zge/
                                PLqVAnwTdyLGE7kunvQ/A3uPWPkmY2YGeFvu9Kpg8oBiwLy9iEekr57loCH54qd1u5EwnZ9SwAo855/
                                XI7A20bva5YGbdfiAVYSDzaS6nvC+E9ZXwT5P5i7wJbAFagMLRQqAToZB7u9ITDdzhRufUaaZBxOGgMcA5hw0cabXvgzhX/
                                689r460OJIdjPYDO/LMC8BPMjsdNmLlftg2BaZQJn5TnVdLg1vhRn7TuyubKf8DPbd2zmO//zkAAAAASUVORK5CYII=">
                        </div>
                    </div>
                    
                    <nav class="navbar navbar-expand-md navbar-light m-3">
                        <div class="container-fluid justify-content-start">
                            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                                aria-expanded="false" aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon"></span>
                            </button>
                            <a class="navbar-brand ms-3 fs-6">Menu</a>
                            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                                    <li class="nav-item">
                                        <a class="nav-link" href="welcome">Home</a>
                                    </li>
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
                                            role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            Appointment
                                        </a>
                                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                            <li class="ms-3 p-1"><a href="book_appointment">Book Appointment</a></li>
                                            <li class="ms-3 p-1"><a href="ViewAppointment">View Appointment</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </nav>
                </div>
                
                <div class="functional_links">
                    <a href="login">Login</a>
                    <a href="signup">Register</a>
                    <a href="forgot">Find Account/Password</a>
                    <div class="clear"></div>
                </div>
            </div>     
        
            <div>
                <div class="banner_field">
                    <div class="banner">
                        <p>New Patient</p>
                    </div>
                </div>
                
                <div class="signup">
                    <div class="form_heading">
                        <h1>Register</h1>
                        <p>Please fill in the form below</p>
                    </div>

                    <form class="signup_form">
                        <div class="signup_form_flex">                            
                            <div>
                                <label>Username</label>
                                <input type="text" name="signup_username" placeholder="Username">
                            </div>
                            <div>
                                <label>Password</label>
                                <input type="text" name="signup_password" placeholder="Password">
                            </div>
                            <div>
                                <label>First name</label>
                                <input type="text" name="signup_firstname" placeholder="First name">
                            </div>
                            <div>
                                <label>Re-enter Password</label>
                                <input type="text" name="signup_re_enter_password" placeholder="Re-enter password">
                            </div>
                            <div>
                                <label>Last name</label>
                                <input type="text" name="signup_lastname" placeholder="Lastname">
                            </div>
                            <div>
                                <label>Health care number</label>
                                <input type="text" name="signup_healthcare_num" placeholder="Health care number">
                            </div>
                            <div>
                                <label>Phone number</label>
                                <input type="tel" name="signup_phonenum" placeholder="Phone number">
                            </div>
                            <div>
                                <label>Email</label>
                                <input type="text" name="signup_email" placeholder="Email">
                            </div>
                            <div>
                                <label>Alternate Phone number</label>
                                <input type="tel" name="signup_phonenum_alt" placeholder="Alternate phone number">
                            </div>
                            <div>
                                <label>Birth date</label>
                                <input type="date" name="signup_birth_date" placeholder="Birth date">
                            </div>
                        </div>
                        
                        <div class="signup_radio_btns">
                            <div class="gender">
                                <label>Gender</label>
                                <div class="gender_option">
                                    <div> 
                                        <input type="radio" name="gender_radio">
                                        <label>Male</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="gender_radio">
                                        <label>Female</label>
                                    </div>
                                </div>
                            </div>                            
                            <div class="prefered_notification">
                                <label>Preferred Notification</label>
                                <div class="prefered_notification_option">
                                    <div> 
                                        <input type="radio" name="prefered_notification_radio">
                                        <label>Phone</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="prefered_notification_radio">
                                        <label>Email</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="signup_location">
                            <div class="signup_address_field">
                                <label>Address</label>
                                <br/>
                                <input type="text" name="signup_address" placeholder="Street address">
                                <input type="text" name="signup_address2" placeholder="Street address line 2">
                            </div>
                            <div class="signup_city_state_province">
                                <input type="text" name="signup_city" placeholder="City">
                                <input type="text" name="signup_state_province" placeholder="Province">
                            </div>
                            <div class="signup_postal_code">
                                <input type="text" name="signup_postal" placeholder="Postal code">
                            </div>
                        </div>
                        
                        <div class="register_btn">
                            <input type="submit" value="Register">
                            <input type="button" value="Cancle">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
