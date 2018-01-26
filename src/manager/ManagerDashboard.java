package manager;

/*
 * The ActionListener approach for this class was according to one of Amilcar's Extra Class teachings.
 * He taught us to have a separate class called e.g. "controller" which hosts the ActionListener.
 */

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ManagerDashboard extends JFrame {
	private final int WIDTH = 1100;
	private final int HEIGHT = 800;

	private JPanel panel;
	JPanel content; //this is not private because it is used in ManagerDashboardController.
	JPanel info; //this is not private because it is used in ManagerDashboardController.
	
	private JLabel picture;
	private JLabel welcome;
	private JLabel tcct;
	
	private JButton tl;
	private JButton rb;
	private JButton exit;
	private final int B_WIDTH = 160;
	private final int B_HEIGHT = 50;
	
	private ImageIcon imageIcon;
	
	private JLabel ctValue;
	private JLabel tcot;
	private JLabel otValue;
	private JLabel notice;
	private JLabel tnts;
	private JLabel totalTickets;
	
	private int ticketCost = 50;

	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	private ManagerDashboardController controller = new ManagerDashboardController(this);
	
	public ManagerDashboard(String name) {
		super("Tech Support Ticket Logging System - Manager Dashboard."); //frame title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //it makes the program terminate
		setSize(WIDTH, HEIGHT);  //frame size
		setLocationRelativeTo(null); //set the program to the centre of the screen
		panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
		setContentPane(panel);
		setVisible(true);
		
		UIManager.put("OptionPane.background", Color.white); //set colour for JOptionPane
		UIManager.put("Panel.background", Color.white); //set colour for JOptionPanes
		
		//Ticketing System logo
		imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("../letterT.jpg")).getImage().getScaledInstance(200, 65, Image.SCALE_SMOOTH));
			picture = new JLabel(imageIcon);
			picture.setBounds(970, 10, 100, 58);
			getContentPane().add(picture);
		
		welcome = new JLabel("Welcome " + name + "!");
			welcome.setFont(font);
			welcome.setBounds(10, 11, 258, 44);
			panel.add(welcome);

		graphInfo(); //I had to create this method because of the "refresh" button
		usefulInfo(); //I had to create this method because of the "refresh" button

		tl = new JButton("Ticket Lifetime");
			tl.setBounds(450, 700, B_WIDTH, B_HEIGHT);
			tl.addActionListener(controller);
			tl.setActionCommand("tl");
			panel.add(tl);
			
		rb = new JButton("Refresh");
			rb.setBounds(65, 700, B_WIDTH, B_HEIGHT);
			rb.addActionListener(controller);
			rb.setActionCommand("refresh");
			panel.add(rb);
			
		exit = new JButton("Exit");
			exit.setBounds(860, 700, B_WIDTH, B_HEIGHT);
			exit.addActionListener(controller);
			exit.setActionCommand("exit");
			panel.add(exit);
			
		validate();//used to validate the container after adding or removing components.
		repaint(); //used to repaint the container after adding or removing components.
	}
	
	public JPanel graphInfo() {
		content = new JPanel(); //panel with the graph
		content.setBounds(10, 66, 1060, 510);
		content.add(controller.barGraph());
		panel.add(content);
		
		return content;
	}
	
	public JPanel usefulInfo() {
		info = new JPanel(); //panel with useful information
			info.setBounds(10, 585, 1060, 100);
			info.setLayout(null);
			panel.add(info);
			
		tcot = new JLabel("Total Cost of OPEN Tickets*:");
			tcot.setFont(new Font("Arial", Font.PLAIN, 20));
			tcot.setBounds(150, 10, 290, 40);
			info.add(tcot);
			
		otValue = new JLabel();
			otValue.setFont(font);
			otValue.setForeground(Color.RED);
			otValue.setText("€" + (OpenTicketsData.totalOpenTickets() * ticketCost));
			otValue.setBounds(420, 10, 100, 40);
			info.add(otValue);
			
		tcct = new JLabel("Total Cost of CLOSED Tickets*:");
			tcct.setFont(font);
			tcct.setBounds(550, 10, 290, 40);
			info.add(tcct);
			
		ctValue = new JLabel();
			ctValue.setFont(font);
			ctValue.setForeground(Color.RED);
			ctValue.setText("€" + (ClosedTicketsData.totalClosedTickets() * ticketCost));
			ctValue.setBounds(850, 10, 100, 40);
			info.add(ctValue);
			
		tnts = new JLabel("Total Number of Tickets in the System:");
			tnts.setFont(new Font("Arial", Font.PLAIN, 20));
			tnts.setBounds(350, 60, 370, 40);
			info.add(tnts);
			
		totalTickets = new JLabel();
			totalTickets.setText("" + (OpenTicketsData.totalOpenTickets() + ClosedTicketsData.totalClosedTickets()));
			totalTickets.setForeground(Color.RED);
			totalTickets.setFont(new Font("Arial", Font.PLAIN, 20));
			totalTickets.setBounds(710, 60, 100, 40);
			info.add(totalTickets);
			
		notice = new JLabel("*Each ticket costs €" + ticketCost + ".");
			notice.setFont(new Font("Arial", Font.PLAIN, 12));
			notice.setBounds(920, 70, 130, 20);
			info.add(notice);
		
		return info;
	}
}
