package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import main.DBConnection;

public class ClosedTicketsData {
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String sql;
	static String data;
	static int total;

	public static int jamesClosedTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE responsible = 'James' AND status = 'Closed';";
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
	
	public static int johanClosedTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE responsible = 'Johan' AND status = 'Closed';";
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
	
	public static int johnClosedTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE responsible = 'John' AND status = 'Closed';";
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
	
	public static int totalClosedTickets() {
		DBConnection.connector();
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT COUNT(id) FROM ticket WHERE status = 'Closed';";
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
