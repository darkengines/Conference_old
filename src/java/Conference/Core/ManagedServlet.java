/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Core;

import Conference.Database.DAOConfigurationException;
import Conference.Database.DAOException;
import Conference.User.User;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Quicksort
 */
public class ManagedServlet extends HttpServlet {
    
    protected Authorization authorization;
    protected boolean checkAuthorization(HttpServletRequest request) throws SQLException, DAOException, DAOException, DAOConfigurationException {
        return User.getAuthorization(request) == authorization;
    }    
}
