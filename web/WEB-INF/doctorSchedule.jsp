<%-- 
    Document   : doctorSchedule
    Created on : Feb 11, 2022, 3:48:16 PM
    Author     : 816386
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <style><%@include file="/WEB-INF/styleWelcomePage.css"%></style>
        <style><%@include file="/WEB-INF/styleSchedulePage.css"%></style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
        <title>Doctor Schedule Page</title>
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
        <div class="doctor_schedule_banner">
            <img src="img/doctor_schedule.png" width="100%"/>
            <p class="doctor_schedule_banner_text">Doctor schedule</p>
            
        </div>
        
         <div class="schedule-wrapper">
            <div id="schedule">
                <div id="form_heading">
                        <header>
                            <span id="hdr-Id"></span>
                            <span class="hdr-radiobutton"></span>
                            <span id="hdr-FirstName">FIRST NAME</span>
                            <span id="hdr-LastName">LAST NAME</span>
                            <span id="hdr-Date">DATE</span>
                            <span id="hdr-Time">TIME</span>
                        </header>

                </div>

        <form id="schedule_form">
                    <div id="schedule_form_flex">
                            
                        <!-- mockup -->
                            <!--<div class="Row" id="row-0">
                                <span class="Row-radiobutton"><input type="radio" id="select" 
                                            name="select" value="0" style="margin-right:50px ;" onchange="ToggleClassState('confirmation-popup','hidden',true)"></span>
                                    <span class="Row-FirstName">Doug </span>
                                    <span class="Row-LastName">Shier</span>
                                    <span class="Row-Date">Thu, 02 Dec</span>
                                    <span class="Row-Time">11:00 am</span>
                            </div>-->
            

                            <br>
                            <!-- for now use this link to go to confirmation page-->
                       <li><a href="confirmation">appt confirmation </a></li>
                       <!-- The button below does not work -->
                       <form action="appointmentConfirmation">
                           <input type="submit" value="Appointment" class="confirm_button">
                       </form>
                    </div>
                 
                </form>

            </div>
        </div>

        

    </body>
</html>
