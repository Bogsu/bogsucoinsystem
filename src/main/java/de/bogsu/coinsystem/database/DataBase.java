package de.bogsu.coinsystem.database;

import java.sql.*;

/**
 * DataBase - Diese Klasse stellt Methoden bereit um Datensätze in der Datenbank hinzuzufügen, zu löschen und zu bearbeiten
 */
public class DataBase {
    private static final String USERNAME = "sql7374508";
    private static final String PASSWORD = "CCIV2i85pQ";
    private static final String CONN_STRING = "jdbc:mysql://sql7.freesqldatabase.com/sql7374508";

    /**
     * Methode um ein Konto aus der Datenbank zu löschen
     */
    public void deleteAccount(String accountname, String username) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

            String sql = "DELETE FROM tblAccount " +
                    "WHERE accountname = ? AND username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, accountname);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Methode um ein neues Konto hinzuzufügen
     */
    public void insertAccount(String accountname, String username, int credit) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

            String sql = "INSERT INTO tblAccount (accountname, username, credit)" +
                    "VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, accountname);
            preparedStatement.setString(2, username);
            preparedStatement.setInt(3, credit);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

        } catch (ClassNotFoundException e1) {
            System.err.println(e1);
        } catch (SQLException e2) {
            System.err.println(e2);
        }
    }

    /**
     * Methode um den aktuellen Kontostand eines Kontos auszulesen
     */
    public int selectCredit(String accountname, String username) {
        Connection conn = null;
        int credit = -1;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

            String sql = "SELECT credit FROM tblAccount WHERE accountname = ? AND username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, accountname);
            preparedStatement.setString(2, username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                credit = rs.getInt("credit");
            }
            preparedStatement.close();

        } catch (ClassNotFoundException e1) {
            System.err.println(e1);
        } catch (SQLException e2) {
            System.err.println(e2);
        }

        return credit;
    }

    /**
     * Methode um alle Konten eines Benutzers auszulesen
     */
    public String selectAccounts(String username) {
        Connection conn = null;
        String result = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

            String sql = "SELECT accountname FROM tblAccount WHERE username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                result = result + rs.getString("accountname")+", ";
            }
            preparedStatement.close();
            conn.close();

        } catch (ClassNotFoundException e1) {
            System.err.println(e1);
        } catch (SQLException e2) {
            System.err.println(e2);
        }

        return result;
    }

    /**
     * Methode um den Kontostand eines Kontos zu ändern
     */
    public void updateCredit(String accountname, String username, int credit){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

            String sql = "UPDATE tblAccount " +
                    "SET credit = ? " +
                    "WHERE accountname = ? AND username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(credit));
            preparedStatement.setString(2, accountname);
            preparedStatement.setString(3, username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

        } catch (ClassNotFoundException e1) {
            System.err.println(e1);
        } catch (SQLException e2) {
            System.err.println(e2);
        }
    }
}
