/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.User;

import Conference.Core.Authorization;
import Conference.Core.ManagedServlet;
import Conference.Database.DAOConfigurationException;
import Conference.Database.DAOException;
import Conference.Database.DAOFactory;
import Conference.Database.UserDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class Register extends ManagedServlet {
    
    public static final String DAO_ATTRIBUTE =  "daofactory";
    public static final String USER_ATTRIBUTE = "user";
    public static final String FORM_ATTRIBUTE = "form";
    public static final String VIEW =           "/WEB-INF/Register.jsp";
    
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
        try {
            UserForm form = new UserForm(userDao);
            User user = form.RegisterUser(request);
            request.setAttribute(USER_ATTRIBUTE, user);
            request.setAttribute(FORM_ATTRIBUTE, form);
            
           

            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
