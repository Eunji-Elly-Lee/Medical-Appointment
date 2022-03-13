<%-- 
    Document   : Login page
    Created on : Feb 5, 2022
    Author     : Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">  
        <style><%@ include file="/css/styleLoginPage.css" %></style>  
        <title>Login Page</title>
    </head>
    
    <body>
        <a href="welcome">return to previous page</a>
        <div>
            <form class="login_field" action="login" method="POST">
                <h1>Surpass Health Clinic</h1>
                <h3>Login</h3>
                <label>Username</label>
                <input type="text" name="username_input" size="30">
                <br/>
                <label>Password</label>
                <input type="text" name="password_input" size="30">
                <br>
                <br>
                <input id="login_btn" type="submit" value="Log in" size="30">
                <br>
                <br>
                <a class="forget_pwd" href="forgot"> Forgot your password?</a>
                <br>
            </form>
            <span>Need an account?</span>
            <a class="sign_up" href="signup">Sign up</a>
        </div>
    </body>
</html>
