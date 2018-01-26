package main;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import admin.AdminDashboard;
import manager.ManagerDashboard;
import tech.TechSupportDashboard;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {
	private final int WIDTH = 700;
	private final int HEIGHT = 350;
	
	private JPanel panel;
	
	private ImageIcon imageIcon;
	
	private JLabel title;
	private JLabel picture;
	private	JLabel username;
	private JLabel password;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JButton login;
	private JButton cancel;
	
	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	public Login() {
		super("Tech Support Ticket Logging System."); //frame title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //it makes the program terminate
		setSize(WIDTH, HEIGHT); //frame size
		setLocationRelativeTo(null); //sets the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(SystemColor.white);
			panel.setLayout(null);
		setContentPane(panel); //pane property
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPane
		
		//Ticketing System logo
		imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("../letterT.jpg")).getImage().getScaledInstance(400, 240, Image.SCALE_SMOOTH));
			
		picture = new JLabel(imageIcon);
			picture.setBounds(10, 0, 289, 296);
			getContentPane().add(picture);
		
		title = new JLabel("Login");
			title.setFont(new Font("Arial Black", Font.PLAIN, 30));
			title.setBounds(431, 0, 122, 50);
			panel.add(title);

		username = new JLabel("Username:");
			username.setFont(font);
			username.setBounds(309, 54, 122, 44);
			panel.add(username);
		
		usernameField = new JTextField(10);
			usernameField.setBounds(300, 95, 360, 45);
			panel.add(usernameField);
		
		password = new JLabel("Password:");
			password.setFont(font);
			password.setBounds(309, 150, 122, 44);
			panel.add(password);
			
		passwordField = new JPasswordField();
			passwordField.setBounds(300, 190, 360, 45);
			panel.add(passwordField);

		login = new JButton("Login");
			login.setBounds(349, 252, 133, 37);
			login.addActionListener(this);
			panel.add(login);
		
		cancel = new JButton("Cancel");
			cancel.setBounds(504, 252, 133, 37);
			cancel.addActionListener(this);
			panel.add(cancel);
		
		validate();//used to validate the container after adding or removing components.
		repaint(); //used to repaint the container after adding or removing components.
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(source == login) {
			loginToDatabase();
		} else
			if(source == cancel) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
	}
	
	public void loginToDatabase() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnection.connector(); //Creating an instance of the database connector controller.
		
		//Connecting
		try {
			conn = DriverManager.getConnection(DBConnection.connection());

			stmt = conn.createStatement(); // Do something with the Connection

			String un = usernameField.getText();
			char[] pwChar = passwordField.getPassword();
			String pw = String.valueOf(pwChar);
			String sql = "SELECT * FROM user WHERE username = '"+un+"' AND password = '"+pw+"'";
			stmt.execute(sql); //Executing the query
			rs = stmt.getResultSet(); //Placing the execution into a variable

			if(!rs.next()) {
				JOptionPane.showMessageDialog(this, "Check your log-in details.");
			} else {
				setVisible(false); //closes the login window
				String user = rs.getString("username");
				user = user.toUpperCase();

				if (rs.getString("type").equals("tech")) {
					new TechSupportDashboard(user);
					setVisible(false);
				}else 
					if (rs.getString("type").equals("admin")) {
						new AdminDashboard(user);
						setVisible(false);
					} else 
						if (rs.getString("type").equals("manager")) {
							new ManagerDashboard(user);
							setVisible(false);
						}
			} 
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}
