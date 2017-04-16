import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import beans.admin;
import tables.adminManager;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
/*
 * 
 *  AdminFrame class
 *  Author: Kapil 
 * 
 */
public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtEmail;
	private JPasswordField pwdPassword;
	private JPasswordField pwdConfirmPassword;

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			AdminFrame frame = new AdminFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public AdminFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminFrame.class.getResource("/img/kiaora-s-icon.png")));
		setTitle("Admin Registration Window");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Enter Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(30, 96, 370, 194);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(37, 23, 100, 20);
		panel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(37, 76, 100, 20);
		panel.add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirmPassword.setBounds(37, 101, 100, 20);
		panel.add(lblConfirmPassword);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(37, 48, 100, 20);
		panel.add(lblEmail);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUsername.setBounds(147, 23, 100, 20);
		panel.add(txtUsername);
		txtUsername.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtEmail.setBounds(147, 48, 202, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);

		pwdPassword = new JPasswordField();
		pwdPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pwdPassword.setBounds(147, 77, 100, 20);
		panel.add(pwdPassword);

		pwdConfirmPassword = new JPasswordField();
		pwdConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pwdConfirmPassword.setBounds(147, 102, 100, 20);
		panel.add(pwdConfirmPassword);

		JButton btnRegister = new JButton("Register");
		btnRegister.setIcon(new ImageIcon(AdminFrame.class.getResource("/img/Save.png")));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button for registering new admin
				try {
					
					String username = txtUsername.getText();
					String email = txtEmail.getText();
					char [] pwd = pwdConfirmPassword.getPassword();
					String password = new String(pwd);
					admin bean = adminManager.getRow(username);
					admin beanEmail = adminManager.getEmail(email);
					 boolean emailStatus = db.validate.validateEmail(email); 
					// Checking if the text fields are empty or not
					if (txtUsername.getText().equals("") || txtEmail.getText().equals("") || password.equals("")) 
					{
						JOptionPane.showMessageDialog(null, "Please enter Username/ Email/ Password", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!(bean == null)) // Checking if the user is already in the database or not
					{
						JOptionPane.showMessageDialog(null, "User already exits", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!(emailStatus)) // Validating Email
					{
						JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!(beanEmail == null)) // Checking if the entered Email is already in the database or not
					{
						JOptionPane.showMessageDialog(null, "This email has already been registered with an User!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} // Confirming the password
					else if (!(new String(pwdPassword.getPassword()).equals(new String(pwdConfirmPassword.getPassword())))) 
					{
						JOptionPane.showMessageDialog(null, "Password does not match!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						admin newBean = new admin();
						// Setting entered data into bean data
						newBean.setUsername(username);
						newBean.setEmail(email);
						newBean.setPassword(password);
						// Inserting the bean into database through adminManager
						boolean result = adminManager.insert(newBean);

						if (result){
							JOptionPane.showMessageDialog(null, "New Admin has been registered! Please return to Login Window to Log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
						} 
					}
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRegister.setBounds(119, 132, 128, 37);
		panel.add(btnRegister);

		JLabel lblLogo = new JLabel("logo");
		lblLogo.setIcon(new ImageIcon(AdminFrame.class.getResource("/img/kiaora-s-icon.png")));
		lblLogo.setBounds(30, 22, 50, 50);
		contentPane.add(lblLogo);

		JLabel lblRegisterAsAn = new JLabel("Register as an admin");
		lblRegisterAsAn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRegisterAsAn.setBounds(100, 22, 300, 50);
		contentPane.add(lblRegisterAsAn);

		JButton btnReturn = new JButton("Return to Login Window");
		btnReturn.setIcon(new ImageIcon(AdminFrame.class.getResource("/img/Back.png")));
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for returning to loginFrame
				dispose();
				loginFrame login = new loginFrame();
				login.setState(NORMAL);
				login.setVisible(true);
			}
		});
		btnReturn.setBounds(119, 301, 216, 39);
		contentPane.add(btnReturn);
	}
}
