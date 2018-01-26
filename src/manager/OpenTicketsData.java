package manager;

import java.sql.*;
import javax.swing.*;
import main.DBConnection;

public class OpenTicketsData {
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String sql;
	static String data;
	static int total;

	public static int jamesOpenTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE responsible = 'James' AND status = 'Open';";
			stmt.execute(sql);
			rs = stmt.getResultSet();
			
			while (rs.next())
				data = rs.getString(1);
		}
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
		}

		return total = Integer.parseInt(data);
	}
	
	public static int johanOpenTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE responsible = 'Johan' AND status = 'Open';";
			stmt.execute(sql);
			rs = stmt.getResultSet();
			
			while (rs.next())
				data = rs.getString(1);
		}
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
		}

		return total = Integer.parseInt(data);
	}
	
	public static int johnOpenTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE responsible = 'John' AND status = 'Open';";
			stmt.execute(sql);
			rs = stmt.getResultSet();
			
			while (rs.next())
				data = rs.getString(1);
		}
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
		}

		return total = Integer.parseInt(data);
	}
	
	public static int totalOpenTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE status = 'Open';";
			stmt.execute(sql);
			rs = stmt.getResultSet();
			
			while (rs.next())
				data = rs.getString(1);
		}
		catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
		}

		return total = Integer.parseInt(data);
	}
}
