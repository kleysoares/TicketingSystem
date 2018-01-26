package main;

import java.sql.*;

import javax.swing.JOptionPane;

public class DBConnection {

	public static String connection() {
		String connection = "jdbc:mysql://127.0.0.1/ticketingsystem?user=root&password=";
		return connection;
	}

	public static Connection connector() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(connection());
			return conn;
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "No connectivity. Please, check your connection details.\n\n " + ex, "No connectivity", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}