/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.User;

import Conference.Core.Authorization;
import Conference.Database.DAOConfigurationException;
import Conference.Database.DAOException;
import Conference.Database.DAOFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quicksort
 */
public class User {
    public static final String USER_SESSION = "current_user";
    public static final String DAOFACTORY_ATTRIBUTE = "daofactory";
    
    public static User getCurrentUser(HttpServletRequest request) throws SQLException, DAOException, DAOConfigurationException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null && (user = (User)session.getAttribute(USER_SESSION)) != null) {
           return user;
        } else {
           return null;
        }
    }
    public static void login(HttpServletRequest request, String email, String password) throws SQLException, DAOException, Exception {
        DAOFactory factory = (DAOFactory)request.getServletContext().getAttribute(DAOFACTORY_ATTRIBUTE);
        User user = factory.getUserDao().getUserFromEmailAndPassword(email, password);
        HttpSession session = request.getSession(false);
        session.setAttribute(USER_SESSION, user);
    }
    private Authorization authorization;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    private Long id;
    private String email;
    private String displayName;
    private String password;
    private Timestamp registrationDate;

    public static Authorization getAuthorization(HttpServletRequest request) throws SQLException, DAOException, DAOConfigurationException {
        User user = User.getCurrentUser(request);
        if (user == null) {
            return Authorization.Anonymous;
        } else {
            return user.authorization;
        }
    }
}
