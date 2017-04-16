import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import beans.passenger;
import tables.passengerManager;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
/*
 * 
 *  mainNavFrame class
 *  Author: Kapil 
 * 
 */
public class mainNavFrame extends JFrame {

	private JPanel contentPane;
	static JLabel lblLoggedInAs;
	private JTextField txtPassportNumber;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JPanel panelPassengerReg;
	
	private DisplayPassengerFrame displayPassenger;
	private int DPF;
	private FlightFrame Flight;
	private int FF;
	private FlightFrame Flight2;
	private int FF2;
	private TicketFrame Ticket;
	private int TF;
	private TicketFrame Ticket2;
	private int TF2;
	private PassengerFrame passengerFrame;
	private int PF;
	/**
	 * Launch the application.
	 */

			public void run() {
				try {
					mainNavFrame frame = new mainNavFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

	/**
	 * Create the frame.
	 */
	public mainNavFrame() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(mainNavFrame.class.getResource("/img/kiaora-s-icon.png")));
		setTitle("Kia Ora Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelMain = new JPanel();
		panelMain.setBounds(5, 5, 784, 511);
		contentPane.add(panelMain);
		panelMain.setLayout(new CardLayout(0, 0));
		
		JPanel panelHome = new JPanel();
		panelMain.add(panelHome, "name_151264273245025");
		panelHome.setLayout(null);
		
		JLabel lblHomeImage = new JLabel("Home Image");
		lblHomeImage.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/kiaora-home.jpg")));
		lblHomeImage.setBounds(28, 0, 355, 511);
		panelHome.add(lblHomeImage);
		
		lblLoggedInAs = new JLabel("Logged in as - ");
		lblLoggedInAs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoggedInAs.setBounds(417, 11, 192, 35);
		panelHome.add(lblLoggedInAs);
		
		JButton btnLogout = new JButton("");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button action for logging out
				dispose();
				loginFrame login = new loginFrame();
				login.setState(NORMAL);
				login.setVisible(true);
				// Tracking number of frames opened and closing if opened
				if (FF >= 1){
					Flight.dispose();
				}
				if (TF >= 1){
					Ticket.dispose();
				}
				if (FF2 >= 1){
					Flight2.dispose();
				}
				if (TF2 >= 1){
					Ticket2.dispose();
				}
				if (DPF >= 1){
					displayPassenger.dispose();
				}
				if (PF >= 1){
				passengerFrame.dispose();
				}
			}
		});
		btnLogout.setOpaque(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBorderPainted(false);
		btnLogout.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/btnLogout.png")));
		btnLogout.setBounds(674, 11, 100, 35);
		panelHome.add(btnLogout);
		
		JPanel pnlReg = new JPanel();
		pnlReg.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlReg.setBounds(417, 57, 340, 132);
		panelHome.add(pnlReg);
		pnlReg.setLayout(null);
		
		JButton btnFlightReg = new JButton("");
		btnFlightReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for displaying flight frame
				Flight = new FlightFrame();
				Flight.setState(NORMAL);
				Flight.setVisible(true);
				Flight.ShowUsersInTable();
				FF = FF + 1;
			}
		});
		btnFlightReg.setVerticalAlignment(SwingConstants.BOTTOM);
		btnFlightReg.setOpaque(false);
		btnFlightReg.setBorderPainted(false);
		btnFlightReg.setContentAreaFilled(false);
		btnFlightReg.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/btnFlight.png")));
		btnFlightReg.setBounds(27, 26, 70, 70);
		pnlReg.add(btnFlightReg);
		
		JLabel lblFlight = new JLabel("Flight");
		lblFlight.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlight.setHorizontalTextPosition(SwingConstants.LEADING);
		lblFlight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFlight.setBounds(27, 98, 70, 23);
		pnlReg.add(lblFlight);
		
		
		JButton btnPassengerReg = new JButton("");
		btnPassengerReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button for displaying passenger registration panel
				panelMain.removeAll();
				panelMain.revalidate();
				panelMain.repaint();
				
				panelMain.add(panelPassengerReg);
				panelMain.revalidate();
				panelMain.repaint();
				
			}
		});
		btnPassengerReg.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/btnPassenger.png")));
		btnPassengerReg.setVerticalAlignment(SwingConstants.BOTTOM);
		btnPassengerReg.setOpaque(false);
		btnPassengerReg.setContentAreaFilled(false);
		btnPassengerReg.setBorderPainted(false);
		btnPassengerReg.setBounds(132, 26, 70, 70);
		pnlReg.add(btnPassengerReg);
		
		JLabel lblPassenger = new JLabel("Passenger");
		lblPassenger.setHorizontalTextPosition(SwingConstants.LEADING);
		lblPassenger.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassenger.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassenger.setBounds(132, 98, 70, 23);
		pnlReg.add(lblPassenger);
		
		JButton btnBookTicket = new JButton("");
		btnBookTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button for displaying TicketFrame
				Ticket = new TicketFrame();
				Ticket.setState(NORMAL);
				Ticket.setVisible(true);
				Ticket.ShowUsersInTable();
				TF = TF + 1;
			}
		});
		btnBookTicket.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/btnTicket.png")));
		btnBookTicket.setVerticalAlignment(SwingConstants.BOTTOM);
		btnBookTicket.setOpaque(false);
		btnBookTicket.setContentAreaFilled(false);
		btnBookTicket.setBorderPainted(false);
		btnBookTicket.setBounds(235, 26, 70, 70);
		pnlReg.add(btnBookTicket);
		
		JLabel lblBookTicket = new JLabel("Book Ticket");
		lblBookTicket.setHorizontalTextPosition(SwingConstants.LEADING);
		lblBookTicket.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookTicket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBookTicket.setBounds(221, 98, 95, 23);
		pnlReg.add(lblBookTicket);
		
		JPanel pnlSearch = new JPanel();
		pnlSearch.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search / Update / Delete", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlSearch.setBounds(417, 206, 340, 84);
		panelHome.add(pnlSearch);
		pnlSearch.setLayout(null);
		
		JButton btnSearchUpdate_F = new JButton("View Flight");
		btnSearchUpdate_F.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button for displaying FlightFrame
				Flight2 = new FlightFrame();
				Flight2.setState(NORMAL);
				Flight2.setVisible(true);
				Flight2.ShowUsersInTable();
				Flight2.selectSearch();
				FF2 = FF2 + 1;
			}
		});
		btnSearchUpdate_F.setBounds(12, 27, 140, 35);
		pnlSearch.add(btnSearchUpdate_F);
		
		JButton btnSearchUpdate_P = new JButton("View Passenger");
		btnSearchUpdate_P.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button for displaying PassengerFrame
				passengerFrame = new PassengerFrame();
				passengerFrame.setState(NORMAL);
				passengerFrame.setVisible(true);
				passengerFrame.ShowUsersInTable();
				PF = PF + 1;
			}
		});
		btnSearchUpdate_P.setBounds(176, 27, 140, 35);
		pnlSearch.add(btnSearchUpdate_P);
		
		JPanel pnlReport = new JPanel();
		pnlReport.setBorder(new TitledBorder(null, "Report", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlReport.setBounds(417, 301, 340, 132);
		panelHome.add(pnlReport);
		pnlReport.setLayout(null);
		
		JButton btnTotalRevenue = new JButton("Total Revenue");
		btnTotalRevenue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button for displaying Report Panel in TicketFrame
				Ticket2 = new TicketFrame();
				Ticket2.setState(NORMAL);
				Ticket2.setVisible(true);
				Ticket2.ShowUsersInTable();
				Ticket2.selectReport();
				TF2 = TF2 + 1;
			}
		});
		btnTotalRevenue.setBounds(98, 72, 145, 37);
		pnlReport.add(btnTotalRevenue);
		
		JButton button = new JButton("Display Passengers\r\n from a Flight");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button for displaying DisplayPassengerFrame
				displayPassenger = new DisplayPassengerFrame();
				displayPassenger.setState(NORMAL);
				displayPassenger.setVisible(true);
				DPF = DPF + 1;
			}
		});
		button.setBounds(34, 22, 275, 37);
		pnlReport.add(button);
		
		JLabel lblCopyright = new JLabel("Developed by Kapil Shrestha | Copyright \u00A9 2016 ");
		lblCopyright.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCopyright.setBounds(416, 446, 341, 26);
		panelHome.add(lblCopyright);
		
		panelPassengerReg = new JPanel();
		panelMain.add(panelPassengerReg, "name_151269665914849");
		panelPassengerReg.setLayout(null);
		
		JLabel label = new JLabel("Home Image");
		label.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/kiaora-home.jpg")));
		label.setBounds(10, 11, 355, 511);
		panelPassengerReg.add(label);
		
		JPanel pnlPassReg = new JPanel();
		pnlPassReg.setBorder(new TitledBorder(null, "Passenger Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlPassReg.setBounds(398, 50, 345, 430);
		panelPassengerReg.add(pnlPassReg);
		pnlPassReg.setLayout(null);
		
		JLabel lblPassportNumber = new JLabel("Passport Number:");
		lblPassportNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassportNumber.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassportNumber.setBounds(42, 57, 109, 24);
		pnlPassReg.add(lblPassportNumber);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFirstName.setBounds(42, 91, 109, 24);
		pnlPassReg.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLastName.setBounds(42, 126, 109, 24);
		pnlPassReg.add(lblLastName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setBounds(42, 165, 109, 24);
		pnlPassReg.add(lblEmail);
		
		txtPassportNumber = new JTextField();
		txtPassportNumber.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtPassportNumber.setBounds(161, 57, 93, 23);
		pnlPassReg.add(txtPassportNumber);
		txtPassportNumber.setColumns(10);
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(162, 91, 124, 23);
		pnlPassReg.add(txtFirstName);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLastName.setColumns(10);
		txtLastName.setBounds(162, 126, 124, 23);
		pnlPassReg.add(txtLastName);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEmail.setColumns(10);
		txtEmail.setBounds(162, 165, 124, 23);
		pnlPassReg.add(txtEmail);
		
		JButton btnRegisterPassenger = new JButton("Register Passenger");
		btnRegisterPassenger.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/Save.png")));
		btnRegisterPassenger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button for registering new passenger
				try {
										
					String passportNo = txtPassportNumber.getText();
					String firstName = txtFirstName.getText();
					String lastName = txtLastName.getText();
					String email = txtEmail.getText();
					passenger bean = passengerManager.getRow(passportNo);
					passenger beanEmail = passengerManager.getEmail(email);
					 boolean emailStatus = db.validate.validateEmail(email); 
					 
					// Checking if the text fields are empty or not
					if (txtPassportNumber.getText().equals("") || txtEmail.getText().equals("") || txtFirstName.getText().equals("") || txtLastName.getText().equals("")) 
					{
						JOptionPane.showMessageDialog(null, "Please enter Passport Number/ Name / Email!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!(bean == null)) // Checking if the entered Passport Number is already in the database or not
					{
						JOptionPane.showMessageDialog(null, "Passport Number already in the database", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!(emailStatus))  // Validating Email
					{
						JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!(beanEmail == null))  // Checking if the entered Email is already in the database or not
					{
						JOptionPane.showMessageDialog(null, "This email has already been registered with a Passenger!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						passenger newBean = new passenger();
						// Setting entered data into bean data
						newBean.setPassportNo(passportNo);	
						newBean.setFirstName(firstName);
						newBean.setLastName(lastName);
						newBean.setEmail(email);
						// Inserting the bean into database through passengerManager
						boolean result = passengerManager.insert(newBean);

						if (result){
							JOptionPane.showMessageDialog(null, "New Passenger has been registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
						} 
					}
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnRegisterPassenger.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRegisterPassenger.setBounds(73, 216, 213, 42);
		pnlPassReg.add(btnRegisterPassenger);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setIcon(new ImageIcon(mainNavFrame.class.getResource("/img/Back.png")));
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button for hiding Registering passenger panel and showing mainNav panel
				panelMain.removeAll();
				panelMain.revalidate();
				panelMain.repaint();
				
				panelMain.add(panelHome);
				panelMain.revalidate();
				panelMain.repaint();
			}
		});
		btnReturn.setBounds(200, 366, 109, 42);
		pnlPassReg.add(btnReturn);
		
		
	}

	public JLabel getLblLoggedInAs() {
		return lblLoggedInAs;
	}
	public JPanel getPanelPassengerReg() {
		return panelPassengerReg;
	}
}
