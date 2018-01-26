package tech;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import main.DBConnection;
import java.time.Instant;

@SuppressWarnings("serial")
public class AddTicket extends JFrame implements ActionListener {
	private final int WIDTH = 550;
	private final int HEIGHT = 320;

	private JPanel panel;
	
	private JLabel title;
	private JLabel team;
	private JLabel issue;
	private JLabel classification;
	private JTextField issueField;
	
	private String[] emp = {"James", "Johan", "John"};
	private JComboBox<Object> res;
	
	private String[] classifications = {"Urgent", "Normal", "Long-term"};
	private JComboBox <Object> options;
	
	private JButton save;
	private JButton close;
	
	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	public AddTicket() {
		super("Tech Support - Add a Ticket."); //frame title
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); //sets the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
		setContentPane(panel); //pane property
		setVisible(true);
		
		title = new JLabel("Add a New Ticket");
			title.setFont(new Font("Arial Black", Font.PLAIN, 30));
			title.setBounds(150, 15, 300, 48);
			panel.add(title);
		
		issue = new JLabel("Issue:");
			issue.setFont(font);
			issue.setBounds(10, 90, 60, 40);
			panel.add(issue);
			
		issueField = new JTextField(10);
			issueField.setBounds(140, 90, 380, 35);
			panel.add(issueField);
		
		team = new JLabel("Assigned To:");
			team.setFont(font);
			team.setBounds(10, 150, 120, 40);
			panel.add(team);
		
		res = new JComboBox<Object>(emp);
			res.setFont(font);
			res.setBackground(Color.WHITE);
			res.setBounds(140, 150, 121, 39);
			panel.add(res);
		
		classification = new JLabel("Classify as:");
			classification.setFont(font);
			classification.setBounds(280, 150, 120, 40);
			panel.add(classification);
		
		options = new JComboBox<Object>(classifications);
			options.setBackground(new Color(255, 255, 255));
			options.setFont(font);
			options.setBounds(400, 150, 121, 39);
			panel.add(options);
		
		save = new JButton("Save");
			save.addActionListener(this);
			save.setBounds(100, 220, 140, 40);
			panel.add(save);
		
		close = new JButton("Close");
			close.addActionListener(this);
			close.setBounds(300, 220, 140, 40);
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
	
	public String optionChoice() { //returns the JComboBox selection
		int op = options.getSelectedIndex();
		String choice = "";
		
		switch(op) {
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

		if(source == save) {
			DBConnection.connector();

			Connection conn = null;
			Statement stmt = null;
			String sql = null;

			try {
				conn = DriverManager.getConnection(DBConnection.connection());

				stmt = conn.createStatement();

				long dt = Instant.now().getEpochSecond();
				String as = resChoice();
				String is = issueField.getText();
				String op = optionChoice();


				if(as.isEmpty() || is.isEmpty() || op.isEmpty()) {
					JOptionPane.showMessageDialog(this, "All fields are required.", "Data input error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					sql = "INSERT INTO ticket (opendate, responsible, issue, classification, status) VALUES ('"+dt+"', '"+as+"', '"+is+"', '"+op+"', 'Open');";
					stmt.execute(sql);
					setVisible(false);
					JOptionPane.showMessageDialog(this, "New ticket added successfully.");
				}
			}
			catch(SQLException ex) {
				JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
			}

		} else
			if(source == close) {
				int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to stop adding a new ticket?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					setVisible(false);
				}
			}
	}
}
