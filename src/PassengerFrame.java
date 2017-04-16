import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import beans.passenger;
import db.DBType;
import db.DBUtil;
import db.ExcelExporter;
import tables.passengerManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
/*
 * 
 *  PassengerFrame class
 *  Author: Kapil 
 * 
 */
public class PassengerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearchPassportNumber;
	private JTextField txtSearchLastName;
	private JTextField txtSUPassportNo;
	private JTextField txtSUFirstName;
	private JTextField txtSULastName;
	private JTextField txtSUEmail;
	private JTextField txtSUPassengerID;
	private static JTable table;
	static Connection conn;
	Statement st;
	ResultSet rs;
	private boolean passengerHasFlight = false;
	
	/**
	 * Launch the application.
	 */
	
			public void run() {
				try {
					PassengerFrame frame = new PassengerFrame();
					frame.setVisible(true);
					findPassengerByLastName();
					findPassengerByPassport();
					ShowUsersInTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	/**
	 * Create the frame.
	 */
	public PassengerFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PassengerFrame.class.getResource("/img/kiaora-s-icon.png")));
		setTitle("Search & Update Passenger | Kia Ora Airlines");
		setResizable(false);
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 784, 511);
		contentPane.add(panel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PassengerFrame.class.getResource("/img/kiaora-s-icon.png")));
		label.setBounds(10, 11, 50, 50);
		panel.add(label);
		
		JLabel lblSUHeading = new JLabel("Search , Update / Delete Passenger");
		lblSUHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblSUHeading.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSUHeading.setBounds(62, 11, 448, 50);
		panel.add(lblSUHeading);
		
		JLabel lblSUSearchP = new JLabel("Search by Passport Number:");
		lblSUSearchP.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSUSearchP.setBounds(80, 82, 158, 24);
		panel.add(lblSUSearchP);
		
		txtSearchPassportNumber = new JTextField();
		txtSearchPassportNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSearchPassportNumber.setColumns(10);
		txtSearchPassportNumber.setBounds(248, 83, 116, 24);
		panel.add(txtSearchPassportNumber);
		
		txtSearchLastName = new JTextField();
		txtSearchLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSearchLastName.setColumns(10);
		txtSearchLastName.setBounds(503, 83, 116, 24);
		panel.add(txtSearchLastName);
		
		JLabel lblSUSearchL = new JLabel("Search by Last Name:");
		lblSUSearchL.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSUSearchL.setBounds(377, 81, 133, 24);
		panel.add(lblSUSearchL);
		
		JButton btnSUSearch = new JButton("Search");
		btnSUSearch.setIcon(new ImageIcon(PassengerFrame.class.getResource("/img/Search.png")));
		btnSUSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Searching Passenger either by Passport number or Last name
				if (txtSearchPassportNumber.getText().equals("") && txtSearchLastName.getText().equals("")) // Checking if both text field is empty or not
				{
					JOptionPane.showMessageDialog(null, "No search query! All records displayed.", "Error", JOptionPane.INFORMATION_MESSAGE);
					ShowUsersInTable();
				} else if (txtSearchLastName.getText().equals(""))
				{
					findPassengerByPassport();
				} else if (txtSearchPassportNumber.getText().equals(""))
				{
					findPassengerByLastName();
				} else
				{
					JOptionPane.showMessageDialog(null, "Please enter one at a time. Either Passport Number or Last Name", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnSUSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSUSearch.setBounds(648, 74, 116, 41);
		panel.add(btnSUSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 134, 479, 348);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Display selected row in JTextField
				int i = table.getSelectedRow();
				TableModel model = table.getModel();
				txtSUPassengerID.setText(model.getValueAt(i, 0).toString());
				txtSUPassportNo.setText(model.getValueAt(i, 1).toString());
				txtSUFirstName.setText(model.getValueAt(i, 2).toString());
				txtSULastName.setText(model.getValueAt(i, 3).toString());
				txtSUEmail.setText(model.getValueAt(i, 4).toString());
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"ID", "Passport No", "First Name", "Last Name", "Email"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(39);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(91);
		table.getColumnModel().getColumn(3).setPreferredWidth(101);
		table.getColumnModel().getColumn(4).setPreferredWidth(149);
		scrollPane.setViewportView(table);
		
		JLabel lblSUPassportNo = new JLabel("Passport Number:");
		lblSUPassportNo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSUPassportNo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSUPassportNo.setBounds(520, 203, 109, 24);
		panel.add(lblSUPassportNo);
		
		txtSUPassportNo = new JTextField();
		txtSUPassportNo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSUPassportNo.setColumns(10);
		txtSUPassportNo.setBounds(639, 203, 93, 23);
		panel.add(txtSUPassportNo);
		
		txtSUFirstName = new JTextField();
		txtSUFirstName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSUFirstName.setColumns(10);
		txtSUFirstName.setBounds(640, 237, 124, 23);
		panel.add(txtSUFirstName);
		
		JLabel lblSUFirstName = new JLabel("First Name:");
		lblSUFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSUFirstName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSUFirstName.setBounds(520, 237, 109, 24);
		panel.add(lblSUFirstName);
		
		JLabel lblSULastName = new JLabel("Last Name:");
		lblSULastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSULastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSULastName.setBounds(520, 272, 109, 24);
		panel.add(lblSULastName);
		
		txtSULastName = new JTextField();
		txtSULastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSULastName.setColumns(10);
		txtSULastName.setBounds(640, 272, 124, 23);
		panel.add(txtSULastName);
		
		txtSUEmail = new JTextField();
		txtSUEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSUEmail.setColumns(10);
		txtSUEmail.setBounds(611, 311, 153, 23);
		panel.add(txtSUEmail);
		
		JLabel lblSUEmail = new JLabel("Email:");
		lblSUEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSUEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSUEmail.setBounds(551, 310, 50, 24);
		panel.add(lblSUEmail);
		
		JButton btnUpdatePassenger = new JButton("Update Passenger");
		btnUpdatePassenger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Updating Passenger
				try {
					 boolean emailStatus = db.validate.validateEmail(txtSUEmail.getText()); 
					 // Checking if the text fields are empty or not
					if (txtSUPassportNo.getText().equals("") || txtSUFirstName.getText().equals("") || txtSULastName.getText().equals("") || txtSUEmail.getText().equals("")) 
					{
						JOptionPane.showMessageDialog(null, "Please enter Passport Number/ Name / Email!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!(emailStatus))  // Validating Email
					{
						JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else 
					{
					String query = "UPDATE `passenger` SET `passportNo`='"+txtSUPassportNo.getText()+"',`firstName`='"+txtSUFirstName.getText()+"',`lastName`='"+txtSULastName.getText()+"',`email`='"+txtSUEmail.getText()+"' WHERE `passengerID` = "+txtSUPassengerID.getText()+"";
					executeSQLQuery(query, "Updated!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnUpdatePassenger.setIcon(new ImageIcon(PassengerFrame.class.getResource("/img/Modify.png")));
		btnUpdatePassenger.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdatePassenger.setBounds(551, 393, 213, 42);
		panel.add(btnUpdatePassenger);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setIcon(new ImageIcon(PassengerFrame.class.getResource("/img/Back.png")));
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		btnReturn.setBounds(648, 20, 116, 41);
		panel.add(btnReturn);
		
		JLabel lblSUPassengerID = new JLabel("Passenger ID:");
		lblSUPassengerID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSUPassengerID.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSUPassengerID.setBounds(520, 168, 109, 24);
		panel.add(lblSUPassengerID);
		
		txtSUPassengerID = new JTextField();
		txtSUPassengerID.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSUPassengerID.setEnabled(false);
		txtSUPassengerID.setEditable(false);
		txtSUPassengerID.setColumns(10);
		txtSUPassengerID.setBounds(639, 168, 50, 23);
		panel.add(txtSUPassengerID);

		JButton btnDeletePassenger = new JButton("Delete Passenger");
		btnDeletePassenger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deleting passenger
				try{
					conn = DBUtil.getConnection(DBType.MYSQL);
					st = conn.createStatement();
					int count = 0;
					String s = "SELECT * FROM `ticket` WHERE CONCAT( `passengerID`)LIKE '%"+txtSUPassengerID.getText().toString()+"%'";
					rs = st.executeQuery(s);
					while(rs.next())
					{
						count = count + 1;
					}

					if (!(count == 0))
					{
						passengerHasFlight=true;
					} else
					{
						passengerHasFlight=false;
					}
					
					if (txtSUPassengerID.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please select a record from the table to delete!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} 
					else if (passengerHasFlight == true)
					{
						JOptionPane.showMessageDialog(null, "You cannot delete this passenger as it is associated with a flight and need for reporting purpose!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
					{
						String query = "DELETE FROM `passenger` WHERE `passengerID` = "+txtSUPassengerID.getText()+"";
						executeSQLQuery(query, "Deleted!");
					}
				}
				 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally{
					try {
						st.close();
						rs.close();
						conn.close();
					} catch (Exception ex){
						JOptionPane.showMessageDialog(null, "ERROR CLOSE");
					}
				}

			}
		});
		btnDeletePassenger.setIcon(new ImageIcon(PassengerFrame.class.getResource("/img/Delete.png")));
		btnDeletePassenger.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDeletePassenger.setBounds(551, 440, 213, 42);
		panel.add(btnDeletePassenger);
		
		JButton btnAddPassenger = new JButton("Add Passenger");
		btnAddPassenger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adding Passenger
				try {
					passenger bean = passengerManager.getRow(txtSUPassportNo.getText());
					passenger beanEmail = passengerManager.getEmail(txtSUEmail.getText());
					 boolean emailStatus = db.validate.validateEmail(txtSUEmail.getText()); 
					
					 // Checking if the text fields are empty or not
					if (txtSUPassportNo.getText().equals("") || txtSUFirstName.getText().equals("") || txtSULastName.getText().equals("") || txtSUEmail.getText().equals("")) 
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
					} else 
					{
						String query = "INSERT INTO `passenger`(`passportNo`, `firstName`, `lastName`, `email`) VALUES ('"+txtSUPassportNo.getText()+"','"+txtSUFirstName.getText()+"','"+txtSULastName.getText()+"','"+txtSUEmail.getText()+"')";
						executeSQLQuery(query, "Added!");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddPassenger.setIcon(new ImageIcon(PassengerFrame.class.getResource("/img/Add.png")));
		btnAddPassenger.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddPassenger.setBounds(551, 344, 213, 42);
		panel.add(btnAddPassenger);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Exporting Passenger data to excel file
				if (table.getModel().getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Nothing to save!");
				}
				else 
				{
					try{
						ExcelExporter exp = new ExcelExporter();
						exp.exportTable(table, new File ("D:/PassengerRecords.xls"));
					} catch (IOException ee){
						ee.getMessage();
					}
				}
			}
		});
		btnExport.setIcon(new ImageIcon(PassengerFrame.class.getResource("/img/Save.png")));
		btnExport.setBounds(514, 20, 124, 41);
		panel.add(btnExport);
	}

	// Arraylist for searching all Passenger records by Last Name
public static ArrayList<passenger> ListByLastName(String lastName)
{
	ArrayList<passenger> usersList = new ArrayList<passenger>();
    
    Statement st;
    ResultSet rs;
    
    try {
    	conn = DBUtil.getConnection(DBType.MYSQL);
    	st = conn.createStatement();
    	String searchQuery = "SELECT * FROM `passenger` WHERE CONCAT( `lastName`)LIKE '%"+lastName+"%'";
    	rs = st.executeQuery(searchQuery);
    	
    	passenger pass;
    	
    	while(rs.next())
    	{
    		pass = new passenger (
    									rs.getInt("passengerID"),
    									rs.getString("passportNo"),
    									rs.getString("firstName"),
    									rs.getString("lastName"),
    									rs.getString("email")
    									);
    		usersList.add(pass);
    	}
    }
    catch(Exception ex){
    	System.out.println(ex.getMessage());
    }
    finally{
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    return usersList;
}

//Display Filtered data from input for Last Name in JTable
public void findPassengerByLastName() {
	
	String lastName = txtSearchLastName.getText();
	
	try {
		ArrayList<passenger> pass = ListByLastName(lastName);
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] {"ID", "Passport No", "First Name", "Last Name", "Email"});
		
		Object[] row = new Object[5];
		
		for(int i = 0; i < pass.size(); i++)
        {
				row[0]= pass.get(i).getPassengerId();
				row[1]= pass.get(i).getPassportNo();
				row[2]= pass.get(i).getFirstName();
				row[3]= pass.get(i).getLastName();
				row[4]= pass.get(i).getEmail();
				model.addRow(row);

			}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(39);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(91);
		table.getColumnModel().getColumn(3).setPreferredWidth(101);
		table.getColumnModel().getColumn(4).setPreferredWidth(149);
	}
	catch (Exception e) {
		e.printStackTrace();
	}

}

//Arraylist for searching all Passenger records by Passport No
public static ArrayList<passenger> ListByPassport(String passportNo)
{
	ArrayList<passenger> usersList = new ArrayList<passenger>();
    
    Statement st;
    ResultSet rs;
    
    try {
    	conn = DBUtil.getConnection(DBType.MYSQL);
    	st = conn.createStatement();
    	String searchQuery = "SELECT * FROM `passenger` WHERE CONCAT( `passportNo`)LIKE '%"+passportNo+"%'";
    	rs = st.executeQuery(searchQuery);
    	
    	passenger pass;
    	
    	while(rs.next())
    	{
    		pass = new passenger (
    									rs.getInt("passengerID"),
    									rs.getString("passportNo"),
    									rs.getString("firstName"),
    									rs.getString("lastName"),
    									rs.getString("email")
    									);
    		usersList.add(pass);
    	}
    }
    catch(Exception ex){
    	System.out.println(ex.getMessage());
    }
    finally{
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    return usersList;
}

//Display Filtered data from input for Passport No in JTable
public void findPassengerByPassport() {
	
	String passportNo = txtSearchPassportNumber.getText();
	
	try {
		ArrayList<passenger> pass = ListByPassport(passportNo);
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] {"ID", "Passport No", "First Name", "Last Name", "Email"});
		
		Object[] row = new Object[5];
		
		for(int i = 0; i < pass.size(); i++)
        {
				row[0]= pass.get(i).getPassengerId();
				row[1]= pass.get(i).getPassportNo();
				row[2]= pass.get(i).getFirstName();
				row[3]= pass.get(i).getLastName();
				row[4]= pass.get(i).getEmail();
				model.addRow(row);

			}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(39);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(91);
		table.getColumnModel().getColumn(3).setPreferredWidth(101);
		table.getColumnModel().getColumn(4).setPreferredWidth(149);
	}
	catch (Exception e) {
		e.printStackTrace();
	}

}

// Arraylist for displaying all passenger records
public static ArrayList<passenger> passengerList()
{
	ArrayList<passenger> passengerList = new ArrayList<passenger>();
    
    Statement st;
    ResultSet rs;
    
    try {
    	conn = DBUtil.getConnection(DBType.MYSQL);
    	st = conn.createStatement();
    	String searchQuery = "SELECT * FROM `passenger` ";
    	rs = st.executeQuery(searchQuery);
    	
    	passenger pass;
    	
    	while(rs.next())
    	{
    		pass = new passenger (
    									rs.getInt("passengerID"),
    									rs.getString("passportNo"),
    									rs.getString("firstName"),
    									rs.getString("lastName"),
    									rs.getString("email")
    									);
    		passengerList.add(pass);
    	}
    }
    catch(Exception ex){
    	System.out.println(ex.getMessage());
    }
    finally{
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    return passengerList;
}

//Display data in JTable
public void ShowUsersInTable(){
	ArrayList<passenger> list = passengerList();
	DefaultTableModel model = new DefaultTableModel();
	model.setColumnIdentifiers(new Object[] {"ID", "Passport No", "First Name", "Last Name", "Email"});
	
	Object[] row = new Object[5];
	
	for(int i = 0; i < list.size(); i++)
    {
			row[0]= list.get(i).getPassengerId();
			row[1]= list.get(i).getPassportNo();
			row[2]= list.get(i).getFirstName();
			row[3]= list.get(i).getLastName();
			row[4]= list.get(i).getEmail();
			model.addRow(row);
		}
	table.setModel(model);
	table.getColumnModel().getColumn(0).setPreferredWidth(39);
	table.getColumnModel().getColumn(1).setPreferredWidth(70);
	table.getColumnModel().getColumn(2).setPreferredWidth(91);
	table.getColumnModel().getColumn(3).setPreferredWidth(101);
	table.getColumnModel().getColumn(4).setPreferredWidth(149);
}

//Execute the SQL query
public void executeSQLQuery(String query, String message)
{
	Statement st;
    
    try {
    	conn = DBUtil.getConnection(DBType.MYSQL);
    	st = conn.createStatement();
    	if(st.executeUpdate(query) == 1)
    	{
    		//Refreshing data in the table
    		DefaultTableModel model = (DefaultTableModel)table.getModel();
    		model.setRowCount(0);
    		ShowUsersInTable();
    		JOptionPane.showMessageDialog(null, "Data "+message+" Successfully");
    	} else
    	{
    		JOptionPane.showMessageDialog(null, "Data Not "+message);
    	}
    }
    catch(Exception ex){
    	ex.printStackTrace();
    }
    finally{
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
}
