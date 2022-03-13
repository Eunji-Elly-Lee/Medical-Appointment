package servlets;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.Account;
import service.AccountService;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class ForgotServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String user_name = (String) session.getAttribute("user_name");
        String query = null;
        
        if (user_name != null && !user_name.equals("")) {
            response.sendRedirect("welcome");
            return;
        } else {
            try {
                query = request.getQueryString().toString();
            } catch (Exception ex) {
                getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                return;
            }

            if (account == null) {
                getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                return;
            } else if (account.getReset_password_uuid() != null) {
                if (query.contains(account.getReset_password_uuid())) {
                    getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                    return;
                } else {
                    getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                    return;
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");        
        AccountService accountService = new AccountService();
        String url = request.getRequestURL().toString();
        String path = getServletContext().getRealPath("/WEB-INF");
        String action = request.getParameter("action");
        
        if (action.equals("findPwd")) {
            String email = request.getParameter("resetEmail");
            
            if (account == null && email != null && !email.equals("")) {
                Account account2 = accountService.resetPassword(email, path, url);
                session.setAttribute("account", account2);
                request.setAttribute("resetMessage", "We sent an email to your email address.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("resetMessage", "You should fill in the blank.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/forgotPassword.jsp").forward(request, response);
                return;
            }
        } else if (action.equals("newPassword")) {
            String newPassword = request.getParameter("resetPassword");
            String resetConfirmPassword = request.getParameter("resetConfirmPassword");
            
            if (newPassword != null && !newPassword.equals("") && resetConfirmPassword != null && !resetConfirmPassword.equals("")
                    && newPassword.equals(resetConfirmPassword)) {
                accountService.changePassword(account.getReset_password_uuid(), newPassword);
                session.removeAttribute("account");
                request.setAttribute("message", "You have successfully changed your password.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            } else if (newPassword != null && !newPassword.equals("") && resetConfirmPassword != null && !resetConfirmPassword.equals("")
                    && !newPassword.equals(resetConfirmPassword)) {
                request.setAttribute("newPswMessage", "Password confirmation does not match.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("newPswMessage", "You should fill in the blank.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                return;
            }
        } else if (action.equals("findAcc")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String emailFind = request.getParameter("email-accountForm");
            
            if (firstName != null && !firstName.equals("")
                    && lastName != null && !lastName.equals("") 
                    && emailFind != null && !emailFind.equals("")) {
                try {
                    Account checkAccount = accountService.sendAccount(emailFind, firstName, lastName, path);
                    
                    if (checkAccount == null) {
                        request.setAttribute("resetPwd", "Check account information again.");
                        
                        getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                        return;
                    } else {
                        request.setAttribute("resetPwd", "We sent an email to your email address.");
                        getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                        return;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                request.setAttribute("resetPwd", "You should fill in the blank.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                return;
            }
        }
    }
}
