package manager;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.text.*;
import java.util.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.DBConnection;

@SuppressWarnings("serial")
public class TicketLifetime extends JFrame {
	private final int WIDTH = 750;
	private final int HEIGHT = 650;

	private JPanel panel;
	private JButton close;
	private JTable table;
	private JScrollPane scroll;
	private JLabel title;
	
	public TicketLifetime() {
		super("Tech Support Ticket Logging System - Ticket Lifetime."); //frame title
		setSize(WIDTH, HEIGHT); //frame size
		setLocationRelativeTo(null); //sets the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
		scroll = new JScrollPane();
			scroll.setBounds(10, 65, 715, 490);
			panel.add(report());
		setContentPane(panel); //pane property
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPane
		
		title = new JLabel("Lifetime of a Closed Ticket");
			title.setFont(new Font("Arial Black", Font.PLAIN, 20));
			title.setBounds(225, 10, 315, 35);
			panel.add(title);
		
		close = new JButton("Close");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to close report?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION)
						setVisible(false);
				}
			});
			close.setBounds(600, 570, 100, 30);
			panel.add(close);
		
		validate();
		repaint();
	}
	
	public JScrollPane report() { //displays all the tickets
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String sql;

		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement();
			sql = "SELECT * FROM ticket WHERE status = 'Closed';";
			stmt.execute(sql);
			rs = stmt.getResultSet();

			//Loop over results
			String[][] data = new String[50][5];
			int counter = 0;

			while(rs.next()){
				String id = rs.getString("id");
				data[counter][0] = id;

				String od = rs.getString("opendate");
				long odEpoch = Long.parseLong(od);
				String odString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date (odEpoch * 1000));
				data[counter][1] = odString;

				String re = rs.getString("responsible");
				data[counter][2] = re;

				String cd = rs.getString("closingdate");
				long cdEpoch = Long.parseLong(cd);
				String cdString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date (odEpoch * 1000));
				data[counter][3] = cdString;

				long difference = cdEpoch - odEpoch;
				String date = new SimpleDateFormat("HH:mm:ss").format(new Date (difference * 1000));
				data[counter][4] = date;

				counter = counter + 1;
			}

			String[] colNames = {"ID", "OPEN DATE", "RESPONSIBLE", "CLOSING DATE", "LIFETIME"};
			table = new JTable(data, colNames);
			scroll.setViewportView(table);

			TableColumnModel tcm = table.getColumnModel();
				tcm.getColumn(0).setPreferredWidth(50); //id
				tcm.getColumn(1).setPreferredWidth(120); //open date
				tcm.getColumn(2).setPreferredWidth(105); //responsible
				tcm.getColumn(3).setPreferredWidth(120); //closing date
				tcm.getColumn(4).setPreferredWidth(90); //lifetime
		}
		catch (SQLException ex) {
			//Handle any errors
			JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
		}
		return scroll;
	}
}
