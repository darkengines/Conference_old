/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class DAOToolkit {
    public static PreparedStatement getPreparedStatement(Connection connection, String sql, boolean returnGeneratedKeys, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i=0;i<objects.length;i++) {
            preparedStatement.setObject(i+1, objects[i]);
        }
        return preparedStatement;
    }
    public static void silentClose(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void silentClose(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void silentClose(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void silentClose(PreparedStatement preparedStatement, Connection connection) {
        silentClose(preparedStatement);
        silentClose(connection);
    }
    public static void silentClose(PreparedStatement preparedStatement, ResultSet resultSet, Connection connection) {
        silentClose(preparedStatement);
        silentClose(resultSet);
        silentClose(connection);
    }
}
