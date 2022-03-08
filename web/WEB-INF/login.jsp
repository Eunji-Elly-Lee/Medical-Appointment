<%-- 
    Document   : login
    Created on : Feb 5, 2022, 3:18:12 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style><%@include file="/WEB-INF/styleLoginPage.css"%></style>
    </head>
    <body>
        <a  class="return to previous page" href="welcome">return to previous page</a>
        <div >
            <form id="login_field" action="login" method="POST">
                <h1>Surpass Health Clinic</h1>
                <h3>Login</h3>
                <label>Username</label>
                <input type="text" name="username_input" size="30"/>
                <br/>
                <label>Password</label>
                <input type="text" name="password_input" size="30"/>
                <br/>
                <br/>
                <input id="login_btn" type="submit" value="Log in" size="30" />
                <br/>
                <br>
                <a id="forget_pwd" href="forgot"> Forgot your password?</a>
                <br/>



            </form>
            <span>Need an account?</span>
            <a id="sign_up" href="signup">Sign up</a>
        </div>
    </body>
</html>
