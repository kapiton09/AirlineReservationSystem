import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import tables.adminManager;
import beans.admin;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.awt.event.ActionEvent;
/*
 * 
 *  loginFrame class : Main method
 *  Author: Kapil 
 * 
 */
public class loginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtpasswordField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginFrame frame = new loginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void loginClose() {
		this.dispose();
	}

	/**
	 * Create the frame.
	 */
	
	public loginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(loginFrame.class.getResource("/img/kiaora-s-icon.png")));
		setTitle("Kia Ora Airlines Reservation System");
		setResizable(false);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelEnterDetails = new JPanel();
		panelEnterDetails.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Please Login", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelEnterDetails.setBounds(47, 228, 350, 185);
		contentPane.add(panelEnterDetails);
		panelEnterDetails.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(40, 34, 90, 18);
		panelEnterDetails.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(40, 69, 90, 18);
		panelEnterDetails.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Username");
		txtUsername.setForeground(new Color(0, 0, 0));
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtUsername.setBounds(140, 29, 135, 29);
		panelEnterDetails.add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnLogin = new JButton("");
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setOpaque(false);
		btnLogin.setIcon(new ImageIcon(loginFrame.class.getResource("/img/btnLogin.png")));
		btnLogin.setDisabledIcon(new ImageIcon(loginFrame.class.getResource("/img/btnLogin.png")));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button Action for logging in admin
				if (txtUsername.getText().equals("")) { // Checking if the user name text field is empty or not
					JOptionPane.showMessageDialog(null, "Please enter Username", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(txtpasswordField.getPassword().equals("")) { // Checking if the password field is empty or not
					JOptionPane.showMessageDialog(null, "Please enter Password", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String username = txtUsername.getText(); // User typed USERNAME
						char [] pwd = txtpasswordField.getPassword(); // User typed PASSWORD
						String password = new String(pwd);
						
						admin bean = adminManager.getRow(username); // it filter outs user typed username in the database if available
						
						if (bean == null){ // if no user found the following action will be performed
							JOptionPane.showMessageDialog(null, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
						} else { // if user found the following action will be performed
							String pass = bean.getPassword(); 
							if (password.equals(pass)){ // validating if password is correct for the username
								JOptionPane.showMessageDialog(null, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
								loginClose();
								mainNavFrame main = new mainNavFrame();
								mainNavFrame.lblLoggedInAs.setText("WELCOME, " + username + "!");
								main.setVisible(true);
							} else{
								JOptionPane.showMessageDialog(null, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLogin.setBounds(97, 120, 150, 54);
		panelEnterDetails.add(btnLogin);
		
		txtpasswordField = new JPasswordField();
		txtpasswordField.setEchoChar('*');
		txtpasswordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpasswordField.setToolTipText("Password is case sensitive!");
		txtpasswordField.setBounds(140, 66, 135, 28);
		panelEnterDetails.add(txtpasswordField);
		
		JCheckBox chckbxShowPassword = new JCheckBox("Show Password");
		chckbxShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// To show password from password field
				if(chckbxShowPassword.isSelected()){
					txtpasswordField.setEchoChar((char)0);
				} else {
					txtpasswordField.setEchoChar('*');
				}
			}
		});
		chckbxShowPassword.setBounds(140, 101, 135, 23);
		panelEnterDetails.add(chckbxShowPassword);
		
		JLabel lblNotRegistered = new JLabel("Not registered?");
		lblNotRegistered.setBounds(107, 441, 102, 14);
		contentPane.add(lblNotRegistered);
		
		JButton btnNewRegister = new JButton("Register here");
		btnNewRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button Action for displaying AdminFrame
				loginClose();
				AdminFrame register = new AdminFrame();
				register.setVisible(true);
				
			}
		});
		btnNewRegister.setBounds(217, 437, 116, 23);
		contentPane.add(btnNewRegister);
		
		JLabel label = new JLabel("Welcome to Kia Ora Registration System");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(47, 11, 364, 22);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(loginFrame.class.getResource("/img/kiaora-icon.png")));
		label_1.setBounds(135, 42, 175, 175);
		contentPane.add(label_1);
	}
}
