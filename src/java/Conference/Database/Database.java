/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conference.Database;

import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
public class Database {
    private static Database instance;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }
    public static Database getInstance() throws ClassNotFoundException, SQLException {
        if (Database.instance == null) {
            Database.instance = new Database();
        }
        return Database.instance;
    }
    private Database() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/conference", "root", "pass");
    }
}
