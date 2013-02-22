/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Bootstrap;

import Conference.Database.DAOConfigurationException;
import Conference.Database.DAOFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Quicksort
 */
public class DAOInitializer implements ServletContextListener {
    
    private static final String DAO_FACTORY_ATTRIBUTE = "daofactory";
    private DAOFactory daoFactory;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            daoFactory = DAOFactory.getInstance();
            context.setAttribute(DAO_FACTORY_ATTRIBUTE, daoFactory);
        } catch (DAOConfigurationException ex) {
            Logger.getLogger(DAOInitializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
