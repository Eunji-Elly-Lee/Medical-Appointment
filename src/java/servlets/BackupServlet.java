package servlets;

import java.io.IOException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import service.*;

/**
 *
 * @author Kevin, Samia, Fied, Yisong, Jihoon, Jonghan, Elly
 */
public class BackupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        AdministratorService ads = new AdministratorService();
        String user_name = (String)session.getAttribute("user_name");        
        
        Account account = null;
        
        try {
            account = as.get(user_name);
            Administrator admin = ads.get(account.getAccount_id());
            
            request.setAttribute("backupAccount", account);
            request.setAttribute("backupUser", admin);
        } catch (Exception ex) {
            Logger.getLogger(BackupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/backup.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
    }
}
