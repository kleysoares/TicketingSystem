package admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import main.DBConnection;

@SuppressWarnings("serial")
public class ResetInfo extends JFrame implements ActionListener {
	private final int WIDTH = 600;
	private final int HEIGHT = 400;

	private JPanel panel;
	
	private JLabel title1;
	private JLabel title2;
	private JLabel userID;
	private JLabel un;
	private JLabel pw;
	private JLabel dept;

	private JRadioButton admin;
	private JRadioButton manager;
	private JRadioButton tech;
	private ButtonGroup group = new ButtonGroup();
	
	private JButton search;
	private JButton save;
	private JButton close;
	private final int B_WIDTH = 100;
	private final int B_HEIGHT = 35;
	
	private JTextField unField;
	private JTextField idField;
	private JTextField pwField;

	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Font titleFont = new Font("Arial Black", Font.PLAIN, 25);
	
	private JTable table;
	private JScrollPane scroll;
	
	public ResetInfo() {
		super("System Admin - Update User Information."); //frame title
		setSize(WIDTH, HEIGHT); //frame size
		setLocationRelativeTo(null); //set the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
		setContentPane(panel);
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPanes
		
		userID = new JLabel("Enter User ID:");
			userID.setFont(font);
			userID.setBounds(130, 10, 140, 35);
			panel.add(userID);
		
		idField = new JTextField(10);
			idField.setBounds(275, 10, 50, 35);
			panel.add(idField);

		search = new JButton("Search");
			search.setBounds(350, 10, B_WIDTH, B_HEIGHT);
			search.addActionListener(this);
			panel.add(search);
			
		title1 = new JLabel("Search Result");
			title1.setFont(titleFont);
			title1.setBounds(200, 65, 200, 40);
			panel.add(title1);
			
		title2 = new JLabel("Update User Information");
			title2.setFont(titleFont);
			title2.setBounds(110, 165, 380, 40);
			panel.add(title2);
			
		un = new JLabel("Username:");
			un.setFont(font);
			un.setBounds(10, 220, 100, 30);
			panel.add(un);

		unField = new JTextField(10);
			unField.setBounds(121, 220, 150, 30);
			panel.add(unField);
			
		pw = new JLabel("Password:");
			pw.setFont(font);
			pw.setBounds(305, 220, 100, 30);
			panel.add(pw);
			
		pwField = new JTextField(10);
			pwField.setBounds(415, 220, 150, 30);
			panel.add(pwField);
			
		dept = new JLabel("Department:");
			dept.setFont(font);
			dept.setBounds(10, 270, 127, 24);
			panel.add(dept);
			
		admin = new JRadioButton("Admin");
			admin.setBackground(Color.white);
			admin.setBounds(175, 270, 100, 25);
			admin.addActionListener(this);
			group.add(admin);
			panel.add(admin);
			
		manager = new JRadioButton("Manager");
			manager.setBackground(Color.white);
			manager.setBounds(280, 270, 100, 25);
			manager.addActionListener(this);
			group.add(manager);
			panel.add(manager);
			
		tech = new JRadioButton("Tech Team");
			tech.setBackground(Color.white);
			tech.setBounds(400, 270, 100, 25);
			tech.addActionListener(this);
			group.add(tech);
			panel.add(tech);
			
		save = new JButton("Save");
			save.setBounds(150, 315, B_WIDTH, B_HEIGHT);
			save.addActionListener(this);
			panel.add(save);
			
		close = new JButton("Close");
			close.setBounds(300, 315, B_WIDTH, B_HEIGHT);
			close.addActionListener(this);
			panel.add(close);
			
		validate();//used to validate the container after adding or removing components.
		repaint(); //used to repaint the container after adding or removing components.
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
		Object source = e.getSource();
		DBConnection.connector();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		if(source == search) {
			try {
				conn = DriverManager.getConnection(DBConnection.connection());

				stmt = conn.createStatement();
				String id = idField.getText();

				if(id.isEmpty())
					JOptionPane.showMessageDialog(this, "ID field is required.", "Data input error", JOptionPane.ERROR_MESSAGE);
				else {
					sql = "SELECT * FROM user WHERE id = '"+id+"' LIMIT 1;";
					stmt.execute(sql);
					rs = stmt.getResultSet();

					// loop over results
					String[][] data = new String[1][4];
					int counter = 0;

					while(rs.next()){
						String id1 = rs.getString("id");
						data[counter][0] = id1;
						String un = rs.getString("username");
						data[counter][1] = un;    	      
						String pw = rs.getString("password");
						data[counter][2] = pw;
						String type = rs.getString("type");
						data[counter][3] = type;

						counter = counter + 1;
					}
					String[] colNames = {"ID", "USERNAME", "PASSWORD", "DEPT"};
					table = new JTable(data, colNames);
					scroll = new JScrollPane(table);
					scroll.setBounds(40, 110, 500, 40);
					panel.add(scroll);
				}
			} catch(SQLException ex) {
				JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
			}

		} else
			if(source == save) {
				try {
					conn = DriverManager.getConnection(DBConnection.connection());

					stmt = conn.createStatement();
					String un = unField.getText();
					String pw = pwField.getText();
					String tp = deptChoice();
					String id = idField.getText();

					if(!un.isEmpty() && !pw.isEmpty() && !tp.isEmpty()) {
						sql = "UPDATE `user` SET `username` = '"+un+"', `password` = '"+pw+"', `type`='"+tp+"' WHERE  `id`= '"+id+"' LIMIT 1;";
						stmt.execute(sql);
						setVisible(false);
						JOptionPane.showMessageDialog(this, "Successful Update.");
					} else
						JOptionPane.showMessageDialog(this, "No Updates Made.", "Data input error", JOptionPane.ERROR_MESSAGE);
				}
				catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
				}
			} else
				if(source == close) {
					int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to stop updating user information?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION)
						setVisible(false);
				}
	}
}