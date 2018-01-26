package tech;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.DBConnection;

@SuppressWarnings("serial")
public class UpdateInfo extends JFrame implements ActionListener {
	private final int WIDTH = 710;
	private final int HEIGHT = 400;

	private JPanel panel;
	
	private JLabel title1;
	private JLabel title2;
	private JLabel ticketID;
	private JLabel res;
	private JLabel is;
	private JLabel clas;
	
	private JButton search;
	private JButton save;
	private JButton del;
	private JButton close;
	private final int B_WIDTH = 100;
	private final int B_HEIGHT = 35;
	
	private JTextField idField;
	private JTextField isField;
	
	private String[] emp = {"James", "Johan", "John"};
	private JComboBox<Object> resp;

	String[] classifications = {"Urgent", "Normal", "Long-term"};
	JComboBox <Object> options;

	private JTable table;
	private JScrollPane scroll;

	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Font titleFont = new Font("Arial Black", Font.PLAIN, 25);
	
	public UpdateInfo() {
		super("System Admin - Update Ticket Information."); //frame title
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
			ticketID.setBounds(150, 10, 150, 35);
			panel.add(ticketID);
		
		idField = new JTextField(10);
			idField.setBounds(325, 10, 50, 35);
			panel.add(idField);

		search = new JButton("Search");
			search.setBounds(425, 10, B_WIDTH, B_HEIGHT);
			search.addActionListener(this);
			panel.add(search);
			
		title1 = new JLabel("Search Result");
			title1.setFont(titleFont);
			title1.setBounds(250, 60, 200, 40);
			panel.add(title1);
			
		title2 = new JLabel("Update Ticket Information");
			title2.setFont(titleFont);
			title2.setBounds(175, 160, 380, 40);
			panel.add(title2);
			
		is = new JLabel("Issue:");
			is.setFont(font);
			is.setBounds(100, 220, 60, 30);
			panel.add(is);
			
		isField = new JTextField(10);
			isField.setBounds(200, 220, 350, 30);
			panel.add(isField);
			
		res = new JLabel("Responsible:");
			res.setFont(font);
			res.setBounds(50, 270, 120, 30);
			panel.add(res);
			
		resp = new JComboBox<Object>(emp);
			resp.setFont(font);
			resp.setBackground(Color.WHITE);
			resp.setBounds(190, 270, 130, 30);
			panel.add(resp);
			
		clas = new JLabel("Classify as:");
			clas.setFont(font);
			clas.setBounds(400, 270, 110, 30);
			panel.add(clas);
			
		options = new JComboBox<Object>(classifications);
			options.setBackground(new Color(255, 255, 255));
			options.setFont(font);
			options.setBounds(530, 270, 130, 30);
			panel.add(options);

		save= new JButton("Save");
			save.setBounds(100, 315, B_WIDTH, B_HEIGHT);
			save.addActionListener(this);
			panel.add(save);
			
		del = new JButton("Delete");
			del.setBounds(300, 315, B_WIDTH, B_HEIGHT);
			del.addActionListener(this);
			panel.add(del);
			
		close = new JButton("Close");
			close.setBounds(500, 315, B_WIDTH, B_HEIGHT);
			close.addActionListener(this);
			panel.add(close);
		
		validate();//used to validate the container after adding or removing components.
		repaint(); //used to repaint the container after adding or removing components.
	}
	
	public String resChoice() {  //returns the JComboBox selection
		int op = options.getSelectedIndex();
		String choice = "";
		
		switch(op) {
		case 0:
			choice = "James";
			break;
		case 1:
			choice = "Johan";
			break;
		case 2:
			choice ="John";
		}
		
		return choice;
	}
	
	public String optionChoice() {  //returns the JComboBox selection
		int opt = options.getSelectedIndex();
		String choice = "";
		
		switch(opt) {
		case 0:
			choice = "Urgent";
			break;
		case 1:
			choice = "Normal";
			break;
		case 2:
			choice ="Long-term";
		}
		
		return choice;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		DBConnection.connector();
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String sql, sql2, sql3;
		String id = idField.getText();
		String res = resChoice();
		String is = isField.getText();
		String op = optionChoice();

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
					scroll.setBounds(10, 102, 678, 55);
					panel.add(scroll);

					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					TableColumnModel tcm = table.getColumnModel();
						tcm.getColumn(0).setPreferredWidth(45); //id
						tcm.getColumn(1).setPreferredWidth(130); //open date
						tcm.getColumn(2).setPreferredWidth(100); //responsible
						tcm.getColumn(3).setPreferredWidth(205); //issue
						tcm.getColumn(4).setPreferredWidth(120); //classification
						tcm.getColumn(5).setPreferredWidth(75); //status
				}
			}
			catch(SQLException ex) {
				JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			if(source == save) {
				try {
					conn = DriverManager.getConnection(DBConnection.connection());

					stmt = conn.createStatement();

					if(!res.isEmpty() && !is.isEmpty() && !op.isEmpty() && !id.isEmpty()) {
						sql2 = "UPDATE ticket SET responsible = '"+res+"', issue = '"+is+"', classification = '"+op+"' WHERE  id = '"+id+"' LIMIT 1;";
						stmt.execute(sql2);
						setVisible(false);
						JOptionPane.showMessageDialog(this, "Successful Update.");
					}
					else
						JOptionPane.showMessageDialog(this, "No Updates Made.\nIssue, Responsible and Classification fields are required.", "Data input error", JOptionPane.ERROR_MESSAGE);
				}
				catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
				if(source == del) {
					int answer = JOptionPane.showConfirmDialog(this, "You are about to delete tiket id " + id + ". \nDo you confirm it?", "Deletion", JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION) {
						setVisible(false);

						if(answer == 0) {
							try {
								conn = DriverManager.getConnection(DBConnection.connection());

								stmt = conn.createStatement();

								if(!id.isEmpty()) {
									sql3 = "DELETE FROM ticket WHERE  id ='"+id+"' LIMIT 1;";
									stmt.execute(sql3);
									setVisible(false);
									JOptionPane.showMessageDialog(this, "Ticket id " + id + " deleted successfully.");
								}
							}
							catch(SQLException ex) {
								JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
							}
						} 
					}
					else
						JOptionPane.showMessageDialog(null, "Deletion not made.");
				}
				else
					if(source == close) {
						int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to stop updating user information?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION) {
							setVisible(false);
						}
					}
	}
}