/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Database;

/**
 *
 * @author Quicksort
 */
public class DAOException extends Exception {
    public DAOException(String message) {
        super(message);
    }
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOException(Throwable cause) {
        super(cause);
    }
}
