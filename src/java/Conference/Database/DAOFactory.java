/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Quicksort
 */
public class DAOFactory {
    private static final String PROPERTIES_FILE   = "Conference/Database/dao.properties";
    private static final String PROPERTY_URL      = "url";
    private static final String PROPERTY_DRIVER   = "driver";
    private static final String PROPERTY_USER     = "user";
    private static final String PROPERTY_PASSWORD = "password";
    
    private String url;
    private String user;
    private String password;
    
    public DAOFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public static DAOFactory getInstance() throws DAOConfigurationException {
        
        Properties properties = new Properties();
        String url;
        String driver;
        String user;
        String password;
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
        
        if (propertiesFile == null) {
            throw new DAOConfigurationException("Property file "+PROPERTIES_FILE+" doesn't exist.");
        }
        
        try {
            properties.load(propertiesFile);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            user = properties.getProperty(PROPERTY_USER);
            password = properties.getProperty(PROPERTY_PASSWORD);
        } catch (IOException e) {
            throw new DAOConfigurationException("Cannot load properties file "+PROPERTIES_FILE+".", e);
        }
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Cannot find driver "+driver+" in classpath.", e);
        }
        
        DAOFactory instance = new DAOFactory(url, user, password);
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    
    public UserDao getUserDao() {
        return new UserDaoImplementation(this);
    }
}
