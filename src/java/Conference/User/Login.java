/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.User;

import Conference.Core.Authorization;
import Conference.Core.ManagedServlet;
import Conference.Database.DAOFactory;
import Conference.Database.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quicksort
 */
public class Login extends ManagedServlet {

    public static final String DAO_ATTRIBUTE =  "daofactory";
    public static final String USER_ATTRIBUTE = "user";
    public static final String FORM_ATTRIBUTE = "form";
    public static final String VIEW =           "/WEB-INF/Login.jsp";
    
    private UserDao userDao;
    
    @Override
    public void init() {
        userDao = ((DAOFactory)getServletContext().getAttribute(DAO_ATTRIBUTE)).getUserDao();
        this.authorization = Authorization.Anonymous;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            LoginForm form = new LoginForm(userDao);
            User user;
            try {
                user = form.loginUser(request);
            } catch (Exception ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute(FORM_ATTRIBUTE, form);
            
           

            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }
}
