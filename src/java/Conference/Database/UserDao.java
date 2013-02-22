/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Database;

import Conference.User.User;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
public interface UserDao {
    public void insert(User user) throws DAOException;
    public User findById(int id) throws DAOException;
    public User findByEmail(String email) throws DAOException;
    public void update(User user, boolean updatePassword) throws DAOException;
    public User getUserFromEmailAndPassword(String email, String password) throws SQLException, DAOException;
}
