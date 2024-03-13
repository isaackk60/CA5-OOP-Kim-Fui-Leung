package org.ca5.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDao {
    /**

     * Author:  Aoife Murphy
     * Other contributors:  Kim Fui Leung, Jamie Duffy Creagh
     * Date: 9-03-24

     */
    private static final String URL = "jdbc:mysql://localhost/";
    private static final String DB_NAME = "library";
    private static final String FULL_URL = URL + DB_NAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(FULL_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find driver class", e);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed", e);
        }
        return connection;
    }

    public void freeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {

        }
    }
}

