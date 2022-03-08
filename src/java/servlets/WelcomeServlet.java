/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.Account;
import service.AccountService;

/**
 *
 * @author ADMIN
 */
public class WelcomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("user_name");
        
        if(request.getParameter("logout") != null) {
            session.invalidate();
            session = request.getSession();
        } else {
            if(user_name != null && !user_name.equals("")) {
                AccountService accountService = new AccountService();

                try {
                    Account user = accountService.get(user_name);
                    request.setAttribute("user", user);
                } catch(Exception ex) {
                    Logger.getLogger(WelcomeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }        
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
        return;        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
}