package admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import main.DBConnection;

@SuppressWarnings("serial")
public class NewUser extends JFrame implements ActionListener {
	private final int WIDTH = 600;
	private final int HEIGHT = 400;

	private JPanel panel;
	
	private ImageIcon imageIcon;
	
	private JLabel picture;
	private JLabel title;
	private JLabel un;
	private JLabel pw;
	private JLabel dept;
	
	private JTextField usernameField;
	private JTextField passwordField;
	
	private JRadioButton admin;
	private JRadioButton manager;
	private JRadioButton tech;
	private ButtonGroup group = new ButtonGroup();
	
	private JButton nuser;
	private JButton close;
	
	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	public NewUser() {
		super("System Admin - New User."); //frame title
		setSize(WIDTH, HEIGHT); //frame size
		setLocationRelativeTo(null); //set the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
		setContentPane(panel);
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPanes
		
		//Ticketing System logo
		imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("../letterT.jpg")).getImage().getScaledInstance(400, 240, Image.SCALE_SMOOTH));
			picture = new JLabel(imageIcon);
			picture.setBounds(10, 80, 250, 230);
			add(picture);
		
		title = new JLabel("Add a New User");
			title.setFont(new Font("Arial Black", Font.PLAIN, 25));
			title.setBounds(185, 15, 233, 44);
			panel.add(title);
			
		un = new JLabel("Username:");
			un.setFont(font);
			un.setBounds(270, 75, 107, 24);
			panel.add(un);
			
		usernameField = new JTextField(10);
			usernameField.setBounds(270, 110, 300, 35);
			panel.add(usernameField);

		pw = new JLabel("Password:");
			pw.setFont(font);
			pw.setBounds(270, 155, 107, 24);
			panel.add(pw);
			
		passwordField = new JTextField(10);
			passwordField.setBounds(270, 190, 300, 35);
			panel.add(passwordField);
			
		dept = new JLabel("Department:");
			dept.setFont(font);
			dept.setBounds(380, 240, 127, 24);
			panel.add(dept);
			
		admin = new JRadioButton("Admin");
			admin.setBackground(Color.white);
			admin.setBounds(285, 270, 80, 25);
			admin.addActionListener(this);
			group.add(admin);
			panel.add(admin);
			
		manager = new JRadioButton("Manager");
			manager.setBackground(Color.white);
			manager.setBounds(375, 270, 80, 25);
			manager.addActionListener(this);
			group.add(manager);
			panel.add(manager);
			
		tech = new JRadioButton("Tech Team");
			tech.setBackground(Color.white);
			tech.setBounds(470, 270, 100, 25);
			tech.addActionListener(this);
			group.add(tech);
			panel.add(tech);
			
		nuser = new JButton("New User");
			nuser.setBounds(305, 315, 100, 35);
			nuser.addActionListener(this);
			nuser.setActionCommand("nuser");
			panel.add(nuser);
			
		close = new JButton("Close");
			close.setBounds(435, 315, 100, 35);
			close.addActionListener(this);
			close.setActionCommand("close");
			panel.add(close);
			
		validate();
		repaint();
	}
	
	public String deptChoice() { //returns the JComboBox selection
		String type = "";
		if(admin.isSelected())
			type = "admin";
		else
			if(manager.isSelected())
				type = "manager";
			else
				if(tech.isSelected())
				type = "tech";
		
		return type;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("nuser")) {
			DBConnection.connector();
			Connection conn = null;
			Statement stmt = null;
			String sql = null;

			try {
				conn = DriverManager.getConnection(DBConnection.connection());

				stmt = conn.createStatement();
				String un = usernameField.getText();
				String pw = passwordField.getText();
				String type = deptChoice();

				if(un.isEmpty() || pw.isEmpty() || type.isEmpty())
					JOptionPane.showMessageDialog(this, "All fields are required.", "Data input error", JOptionPane.ERROR_MESSAGE);
				else {
					sql = "INSERT INTO `user` (`username`, `password`, `type`) VALUES ('"+un+"', '"+pw+"', '"+type+"');";
					stmt.execute(sql);
					setVisible(false);
					JOptionPane.showMessageDialog(this, "New user added successfully.");
				}
			}
			catch(SQLException ex) {
				JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
			}
		} else
			if(e.getActionCommand().equals("close")) {
				int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to stop adding a new user?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
					setVisible(false);
			}
	}
}