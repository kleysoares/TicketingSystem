package admin;

/*
 * The ActionListener approach for this class was "coupled".
 */

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import main.*;

@SuppressWarnings("serial")
public class AdminDashboard extends JFrame implements ActionListener {
	private final int WIDTH = 950;
	private final int HEIGHT = 500;

	private JPanel panel;
	private JScrollPane scroll;
	
	private JLabel picture;
	private JLabel welcome;
	
	private JButton vu;
	private JButton nu;
	private JButton ri;
	private JButton exit;
	private final int B_WIDTH = 200;
	private final int B_HEIGHT = 50;
	
	private ImageIcon imageIcon;
	
	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	public AdminDashboard(String name) {
		super("Tech Support Ticket Logging System - Admin Dashboard."); //frame title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //it makes the program terminate
		setSize(WIDTH, HEIGHT); //frame size
		setLocationRelativeTo(null); //set the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
			setContentPane(panel); //pane property
		scroll = new JScrollPane();
			scroll.setBounds(235, 100, 680, 350);
			panel.add(scroll);
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPanes
		
		//Ticketing System logo
		imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("../letterT.jpg")).getImage().getScaledInstance(670, 340, Image.SCALE_SMOOTH));
		picture = new JLabel(imageIcon);
		scroll.setViewportView(picture);
		
		welcome = new JLabel("Welcome " + name + "!");
			welcome.setFont(font);
			welcome.setBounds(10, 11, 260, 45);
			panel.add(welcome);
			
		vu = new JButton("View Users");
			vu.setBounds(15, 100, B_WIDTH, B_HEIGHT);
			vu.addActionListener(this);
			panel.add(vu);
			
		nu = new JButton("New Users");
			nu.setBounds(15, 200, B_WIDTH, B_HEIGHT);
			nu.addActionListener(this);
			panel.add(nu);
		
		ri = new JButton("Reset User Information");
			ri.setBounds(15, 300, B_WIDTH, B_HEIGHT);
			ri.addActionListener(this);
			panel.add(ri);
			
		exit = new JButton("Exit");
			exit.setBounds(15, 400, B_WIDTH, B_HEIGHT);
			exit.addActionListener(this);
			panel.add(exit);
		
		validate(); //used to validate the container after adding or removing components.
		repaint(); //used to repaint the container after adding or removing components.
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		JTable table = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int numberOfRows = 50;
		DBConnection.connector(); //Creating an instance of the database connector controller.

		if(source == vu) {
			try {
				conn = DriverManager.getConnection(DBConnection.connection());

				stmt = conn.createStatement();
				sql = "SELECT * FROM user ORDER BY username ASC;";
				stmt.execute(sql);
				rs = stmt.getResultSet();

				// loop over results
				String[][] data = new String[numberOfRows][4];
				int counter = 0;

				while(rs.next()){
					String id = rs.getString("id");
					data[counter][0] = id;

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
				scroll.setViewportView(table);

				TableColumnModel tcm = table.getColumnModel();
					tcm.getColumn(0).setPreferredWidth(50); //id
					tcm.getColumn(1).setPreferredWidth(150); //username
					tcm.getColumn(2).setPreferredWidth(150); //password
					tcm.getColumn(3).setPreferredWidth(100); //dept
			}
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage() + "\n\nSQLState: " + ex.getSQLState() + "\n\nVendorError: " + ex.getErrorCode(), "No connectivity", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			if(source == ri) {
				new ResetInfo();
			}
			else 
				if(source == nu)
					new NewUser();
				else
					if(source == exit) {
						int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION)
							System.exit(0);
					}
	}
}
