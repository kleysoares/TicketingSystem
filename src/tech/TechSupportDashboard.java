package tech;

/*
 * The ActionListener approach for this class was "decoupled".
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import main.DBConnection;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class TechSupportDashboard extends JFrame {
	private final int WIDTH = 1100;
	private final int HEIGHT = 600;
	
	private JPanel panel;

	private JLabel picture;
	private JLabel welcome;
	private JLabel tickets;
	
	private JTable table;
	private JScrollPane scroll;
	
	private JButton vot;
	private JButton vct;
	private JButton ant;
	private JButton up;
	private JButton exit;
	private JButton close;
	private final int B_WIDTH = 150;
	private final int B_HEIGHT = 50;
	
	private ImageIcon imageIcon;
	
	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String sql;
	
	public TechSupportDashboard(String name) {
		super("Tech Support Ticket Logging System - Tech Support Dashboard."); //frame title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //it makes the program terminate
		setSize(WIDTH, HEIGHT); //frame size
		setLocationRelativeTo(null); //sets the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
		scroll = new JScrollPane();
			scroll.setBounds(197, 70, 875, 475);
			panel.add(scroll);
		setContentPane(panel); //pane property
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPanes
		
		//Ticketing System logo
		imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("../letterT.jpg")).getImage().getScaledInstance(870, 470, Image.SCALE_SMOOTH));
		picture = new JLabel(imageIcon);
		scroll.setViewportView(picture);
		
		conn = DBConnection.connector(); //this class connects with the database
	
		welcome = new JLabel("Welcome " + name + "!");
			welcome.setFont(font);
			welcome.setBounds(10, 0, 200, 66);
			panel.add(welcome);
		
		tickets = new JLabel("Tech Support");
			tickets.setFont(new Font("Arial Black", Font.PLAIN, 30));
			tickets.setBounds(478, 1, 250, 65);
			panel.add(tickets);
		
		vot = new JButton("View Open Tickets");
			vot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						conn = DriverManager.getConnection(DBConnection.connection()); //it accesses the database
						
						stmt = conn.createStatement();
						sql = "SELECT * FROM ticket WHERE status = 'Open';";
						stmt.execute(sql);
						rs = stmt.getResultSet();
						
						// Loop over results
						String[][] data = new String[50][6];
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
	
							String is = rs.getString("issue");
							data[counter][3] = is;
	
							String cl = rs.getString("classification");
							data[counter][4] = cl;
	
							String st = rs.getString("status");
							data[counter][5] = st;
	
							counter = counter + 1;
						}
							String[] colNames = {"ID", "OPEN DATE", "RESPONSIBLE", "ISSUE", "CLASSIFICATION", "STATUS"};
							table = new JTable(data, colNames);
							scroll.setViewportView(table);
							
							table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							TableColumnModel tcm = table.getColumnModel();
								tcm.getColumn(0).setPreferredWidth(65); //id
								tcm.getColumn(1).setPreferredWidth(150); //open date
								tcm.getColumn(2).setPreferredWidth(150); //responsible
								tcm.getColumn(3).setPreferredWidth(260); //issue
								tcm.getColumn(4).setPreferredWidth(140); //classification
								tcm.getColumn(5).setPreferredWidth(90); //status

					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			vot.setBounds(20, 70, B_WIDTH, B_HEIGHT);
			panel.add(vot);
		
		vct = new JButton("View Closed Tickets");
			vct.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						conn = DriverManager.getConnection(DBConnection.connection());
						
						stmt = conn.createStatement();
						sql = "SELECT * FROM ticket WHERE status = 'Closed';";
						stmt.execute(sql);
						rs = stmt.getResultSet();
						
						//Loop over results
						String[][] data = new String[50][7];
						int counter = 0;

						while(rs.next()) {
							String id = rs.getString("id");
							data[counter][0] = id;

							String od = rs.getString("opendate");
							long odEpoch = Long.parseLong(od);
							String odString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date (odEpoch * 1000));
							data[counter][1] = odString;

							String re = rs.getString("responsible");
							data[counter][2] = re;

							String is = rs.getString("issue");
							data[counter][3] = is;

							String cl = rs.getString("classification");
							data[counter][4] = cl;

							String st = rs.getString("status");
							data[counter][5] = st;

							String cd = rs.getString("closingdate");
							long cdEpoch = Long.parseLong(cd);
							String cdString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date (cdEpoch * 1000));
							data[counter][6] = cdString;
							
							counter = counter + 1;
						}
						
						String[] colNames = {"ID", "OPEN DATE", "RESPONSIBLE", "ISSUE", "CLASSIFICATION", "STATUS", "CLOSING DATE"};
						table = new JTable(data, colNames);
						scroll.setViewportView(table);
						
						table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						TableColumnModel tcm = table.getColumnModel();
							tcm.getColumn(0).setPreferredWidth(50); //id
							tcm.getColumn(1).setPreferredWidth(140); //open date
							tcm.getColumn(2).setPreferredWidth(105); //responsible
							tcm.getColumn(3).setPreferredWidth(240); //issue
							tcm.getColumn(4).setPreferredWidth(110); //classification
							tcm.getColumn(5).setPreferredWidth(70); //status
							tcm.getColumn(6).setPreferredWidth(140); //closing date
					}
					catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			vct.setBounds(20, 150, B_WIDTH, B_HEIGHT);
			panel.add(vct);
			
		ant = new JButton("Add a Ticket");
			ant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddTicket();
				}
			});
			ant.setBounds(20, 235, B_WIDTH, B_HEIGHT);
			panel.add(ant);
		
		up = new JButton("Update a Ticket");
			up.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new UpdateInfo();
				}
			});
			up.setBounds(20, 325, B_WIDTH, B_HEIGHT);
			panel.add(up);
			
		close = new JButton("Close a Ticket");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new CloseTicket();
				}
			});
			close.setBounds(20, 413, B_WIDTH, B_HEIGHT);
			panel.add(close);
		
		exit = new JButton("Exit");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION)
							System.exit(0);
				}
			});
			exit.setBounds(20, 495, B_WIDTH, B_HEIGHT);
			panel.add(exit);
			
		validate(); //used to validate the container after adding or removing components.
		repaint(); //used to repaint the container after adding or removing components.
	}
}
