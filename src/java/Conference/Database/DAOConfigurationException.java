/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Database;

/**
 *
 * @author Quicksort
 */
public class DAOConfigurationException extends Exception {
    public DAOConfigurationException(String message) {
        super(message);
    }
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }
}
