/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Database;

import Conference.User.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
class UserDaoImplementation implements UserDao {
    
    private DAOFactory daoFactory;
    
    public UserDaoImplementation(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void insert(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOToolkit.getPreparedStatement(
                connection,
                "INSERT INTO user (id, email, display_name, password) VALUES (?,?,?,SHA1(?))",
                true,
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getPassword()
            );
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new DAOException("User insert failed.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            } else {
                throw new DAOException("User insert failed, no key returned.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOToolkit.silentClose(preparedStatement, generatedKeys, connection);
        }
    }
    @Override
    public void update(User user, boolean updatePassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = null;
        try {
            connection = daoFactory.getConnection();
            if (updatePassword) {
                sql = "UPDATE user SET id=?, email=?, display_name=?, password=SHA1(?)";
            } else {
                sql = "UPDATE user SET id=?, email=?, display_name=?)";
            }
            preparedStatement = DAOToolkit.getPreparedStatement(
                connection,
                sql,
                true,
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getPassword()
            );
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new DAOException("User update failed.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOToolkit.silentClose(preparedStatement, connection);
        }
    }
    @Override
    public User findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOToolkit.getPreparedStatement(connection, "SELECT id, email, display_name, password FROM user WHERE id=?", false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOToolkit.silentClose(preparedStatement, resultSet, connection);
        }
        return user;
    }
    @Override
    public User findByEmail(String email) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOToolkit.getPreparedStatement(connection, "SELECT id, email, display_name, password FROM user WHERE email=?", false, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOToolkit.silentClose(preparedStatement, resultSet, connection);
        }
        return user;
    }
    public static User mapResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setDisplayName(resultSet.getString("display_name"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
    public User getUserFromEmailAndPassword(String email, String password) throws SQLException, DAOException {
        User user = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Connection connection = null;
        
        connection = daoFactory.getConnection();
        statement = DAOToolkit.getPreparedStatement(connection, "SELECT id, email, display_name, password FROM user WHERE email=? AND password=SHA1(?)", false, email, password);
        result = statement.executeQuery();
        result.next();
        user = mapResultSet(result);
        
        if (user == null) throw new DAOException("User not found.");
        return user;
    }
}
