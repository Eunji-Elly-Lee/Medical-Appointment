<%-- 
    Document   : appointmentConfirmation
    Created on : Feb 11, 2022, 6:53:21 PM
    Author     : 816386
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <style><%@include file="/WEB-INF/styleWelcomePage.css"%></style>
        <style><%@include file="/WEB-INF/styleSchedulePage.css"%></style>
        <style><%@include file="/WEB-INF/styleConfirmationPage.css"%></style>

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
                        <input type="submit" value="Appointment" class="confirm_button">
                    </div>

                </form>

            </div>
        </div>
        <!-- Appointemnt Confimation Popup Box -->
        <div class="overlay">
            <div class="popup">
                <header>Appointment Confirmation</header><br>
                <span class="name">Name</span><br>
                <span id="type">Type</span><br>
                <span id="date">Date</span><br>
                <span id="time">Time</span><br>
                <span id="email">Email</span><br>
                <span id="phone">Phone</span><br>
                <span id="doctor">Doctor</span><br>

                <div class="btn_area_confirm">
                    <!--<button class="btn btn-print">Print</button>
                    <button class="btn btn-close">Close</button>-->
                    <button><a class="print_btn" href="">Print</a></button>
                    <button><a class="close_btn" href="doctorschedule">Close</a></button>

                </div>
            </div>
        </div>


    </body>
</html>
