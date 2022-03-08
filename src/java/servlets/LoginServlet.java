/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.Account;
import service.AccountService;

/**
 *
 * @author ADMIN
 */
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        return;
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = request.getParameter("username_input");
        String password = request.getParameter("password_input");        
        request.setAttribute("username_input", user_name);
        
        AccountService accountService = new AccountService();
        Account account = accountService.login(user_name, password);
        
        if(account == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            return;
        } else {
            session.setAttribute("user_name", user_name);
            response.sendRedirect("welcome");
            return;
        }
    }
}