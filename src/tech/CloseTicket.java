package tech;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import main.DBConnection;

@SuppressWarnings("serial")
public class CloseTicket extends JFrame implements ActionListener {
	private final int WIDTH = 685;
	private final int HEIGHT = 350;

	private JPanel panel;
	
	private JLabel title;
	private JLabel ticketID;
	
	private JTextField idField;

	private JButton search;
	private JButton closeTicket;
	private JButton close;
	
	
	private JTable table;
	private JScrollPane scroll;

	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Font titleFont = new Font("Arial Black", Font.PLAIN, 25);
	
	public CloseTicket() {
		super("System Admin - Close a Ticket."); //frame title
		setSize(WIDTH, HEIGHT); //frame size
		setLocationRelativeTo(null); //set the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
		setContentPane(panel);
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPanes
		
		ticketID = new JLabel("Enter Ticket ID:");
			ticketID.setFont(font);
			ticketID.setBounds(125, 30, 150, 35);
			panel.add(ticketID);
		
		idField = new JTextField(10);
			idField.setBounds(325, 30, 50, 35);
			panel.add(idField);

		search = new JButton("Search");
			search.setBounds(460, 30, 100, 35);
			search.addActionListener(this);
			panel.add(search);
			
		title = new JLabel("Search Result");
			title.setFont(titleFont);
			title.setBounds(250, 90, 200, 40);
			panel.add(title);
			
		closeTicket = new JButton("Close TICKET");
			closeTicket.setBounds(190, 200, 275, 50);
			closeTicket.addActionListener(this);
			panel.add(closeTicket);
			
		close = new JButton("Close window");
			close.setBounds(530, 260, 120, 35);
			close.addActionListener(this);
			panel.add(close);
		
		validate();//used to validate the container after adding or removing components.
		repaint(); //used to repaint the container after adding or removing components.
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		DBConnection.connector();
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String sql, sql2;
		String id = idField.getText();


		if(source == search) {
			try {
				conn = DriverManager.getConnection(DBConnection.connection());

				stmt = conn.createStatement();

				if(id.isEmpty())
					JOptionPane.showMessageDialog(this, "ID field is required.", "Data input error", JOptionPane.ERROR_MESSAGE);
				else {
					sql = "SELECT * FROM ticket WHERE id = '"+id+"' LIMIT 1;";
					stmt.execute(sql);
					rs = stmt.getResultSet();

					// loop over results
					String[][] data = new String[1][6];
					int counter = 0;

					while(rs.next()) {
						String id1 = rs.getString("id");
						data[counter][0] = id1;
						
						String od = rs.getString("opendate");
						long odEpoch = Long.parseLong(od);
						String odString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date (odEpoch * 1000));
						data[counter][1] = odString;
						
						
						String pw = rs.getString("responsible");
						data[counter][2] = pw;
						
						String iss = rs.getString("issue");
						data[counter][3] = iss;
						
						String type = rs.getString("classification");
						data[counter][4] = type;
						
						String status = rs.getString("status");
						data[counter][5] = status;

						counter = counter + 1;
					}
					String[] colNames = {"ID", "OPEN DATE", "RESPONSIBLE", "ISSUE", "CLASSIFICATION", "STATUS"};
					table = new JTable(data, colNames);
					scroll = new JScrollPane(table);
					scroll.setBounds(10, 140, 650, 40);
					panel.add(scroll);

					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					TableColumnModel tcm = table.getColumnModel();
						tcm.getColumn(0).setPreferredWidth(40); //id
						tcm.getColumn(1).setPreferredWidth(130); //open date
						tcm.getColumn(2).setPreferredWidth(100); //responsible
						tcm.getColumn(3).setPreferredWidth(180); //issue
						tcm.getColumn(4).setPreferredWidth(120); //classification
						tcm.getColumn(5).setPreferredWidth(75); //status
				}
			}
			catch(SQLException ex) {
				JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			if(source == closeTicket) {
				if(id.isEmpty())
					JOptionPane.showMessageDialog(this, "Enter a valid ticket id above.", "Data input error", JOptionPane.ERROR_MESSAGE);
				else {
					int answer = JOptionPane.showConfirmDialog(this, "You are about to close tiket id " + id + ". \nDo you confirm it?", "Closing a Ticket", JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION) {
						//setVisible(false);

						if(answer == 0) {
							try {
								conn = DriverManager.getConnection(DBConnection.connection());

								stmt = conn.createStatement();

								long dt = Instant.now().getEpochSecond();
								sql2 = "UPDATE ticket SET status = 'Closed', closingdate = '"+dt+"' WHERE  id = '"+id+"';";
								stmt.execute(sql2);
								setVisible(false);
								JOptionPane.showMessageDialog(this, "Ticket id " + id + " was CLOSED successfully.");
							}
							catch(SQLException ex) {
								JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
							JOptionPane.showMessageDialog(null, "Ticket id "+ id +" was not closed.");
					}
				}
			}
			else
				if(source == close) {
					int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit window?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION) {
						setVisible(false);
					}
				}
	}
}