<%-- 
    Document   : bookAppointment
    Created on : Feb 10, 2022, 3:52:00 PM
    Author     : 854276
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <style><%@include file="/WEB-INF/styleWelcomePage.css"%></style>
        <style><%@include file="/WEB-INF/styleBookPage.css"%></style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
        <title>Book Appointment Page</title>
    </head>
    <body>
        <div>
            <div class="fixed_position_welcome_page">


                <div class="top_blue_div">

                </div>
                <div class="topnav-wrapper">
                    <div class="topnav">
                        <div class="topnav-left">


                            <a href="welcome"><img src="img/logo.png" height="88px"></a>
                        </div>
                        <div class="topnav-right">
                            <nav>
                                <ul>
                                    <li><a href="">Home</a></li>
                                    <li><a class="drop-down-tab" href="">Appointment <i class="bi bi-caret-down-fill"></i></a>
                                        <ul class="subnav">
                                            <li><a href="">Book Appointment</a></li>
                                            <li><a href="">View Appointment</a></li>
                                        </ul>
                                    </li>
                                    <li><a href="schedule">Doctor schedule</a></li>

                                </ul>
                            </nav>
                        </div>


                    </div>
                </div>
            </div>
            <br/>
            <div id="functional_links_welcomePage">
                <a href="login">Manage account</a>
                <a href="signup">Log out</a>
                <div class="clear"></div>
            </div>

        </div>



        <div class="book_appointment_banner">
            <img src="img/book_appointment.png" width="100%"/>
            <p class="book_appointment_banner_text">Book Appointment</p>

        </div>



        <div class="book-wrapper">
            <div id="book">
                <div id="form_heading">
                    <h1>Book Appointment</h1>
                    <p>Please fill in the form below</p>
                </div>

                <form id="book_form">
                    <div id="book_form_flex">


                        <div>
                            <label>Type</label>
                            <input type="text" name="signup_username" placeholder="Type"/>
                        </div>
                        <div>
                            <label>Assigned Doctor</label>
                            <input type="text" name="signup_password" placeholder="Doctor"/>
                        </div>

                        <div>
                            <label>Appointment Date</label>
                            <input type="date" name="book_app_date">
                        </div>
                        <div>
                            <label>Time</label>
                            <input type="time" id="appt" name="appt">
                        </div>
                        <div>
                            <label>Appointment Reason</label>
                            <textarea rows="4" cols="50" name="book_app_reason" placeholder="Appointment reason">

                            </textarea>

                            <br>
                            <form action="schedule">
                                <input type="submit" value="Continue" class="confirm_button">
                            </form>
                        </div>


                    </div>




                </form>

            </div>
        </div>


    </body>
</html>
