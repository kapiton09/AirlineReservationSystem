import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import beans.flight;
import beans.passenger;
import db.DBType;
import db.DBUtil;
import db.ExcelExporter;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
/*
 * 
 *  FlightFrame class
 *  Author: Kapil 
 * 
 */
public class FlightFrame extends JFrame {

	private JPanel contentPane;
	private static JTable table;
	private JTextField txtAddFare;
	private JFormattedTextField frmtdtxtfldTimeAdd;
	private JFormattedTextField frmtdtxtfldTimeUpdate;
	private JFormattedTextField frmtdtxtfldDateUpdate;
	private JComboBox cbxAddOrigin;
	private JComboBox cbxAddDest;
	private JSpinner spinnerCapacityUpdate;
	private static JDateChooser dateChooserSearch;
	private boolean flightHasPassenger = false;

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			FlightFrame frame = new FlightFrame();
			frame.setVisible(true);
			findFlightByOD();
			ShowUsersInTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Connection conn;
	Statement st;
	ResultSet rs;
	private JTextField txtFlightUpdateId;
	private JTextField txtUpdateFare;
	private static JTextField txtSearchByO;
	private static JTextField txtSearchByD;
	private static JTextField txtSearchByDd;
	private JTextField txtOriginUpdate;
	private JTextField txtDestinationUpdate;
	private static JPanel panel_add;
	private static JTabbedPane tabbedPane;

	/**
	 * Create the frame.
	 */
	public FlightFrame() {
		setTitle("Flight Registration");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FlightFrame.class.getResource("/img/kiaora-s-icon.png")));
		setBounds(100, 100, 856, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 850, 521);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		scrollPane.setBounds(30, 113, 434, 382);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Display selected row in JTextField
				int i = table.getSelectedRow();
				TableModel model = table.getModel();
				txtFlightUpdateId.setText(model.getValueAt(i, 0).toString());
				txtOriginUpdate.setText(model.getValueAt(i, 1).toString());
				txtDestinationUpdate.setText(model.getValueAt(i, 2).toString());
				frmtdtxtfldDateUpdate.setText(model.getValueAt(i, 3).toString());
				frmtdtxtfldTimeUpdate.setText(model.getValueAt(i, 4).toString());
				txtUpdateFare.setText(model.getValueAt(i, 6).toString());
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Flight ID", "Origin", "Destination", "Date", "Time", "Capacity", "Fare"
				}
				));
		table.getColumnModel().getColumn(0).setPreferredWidth(51);
		table.getColumnModel().getColumn(1).setPreferredWidth(47);
		table.getColumnModel().getColumn(2).setPreferredWidth(65);
		table.getColumnModel().getColumn(3).setPreferredWidth(67);
		table.getColumnModel().getColumn(4).setPreferredWidth(56);
		table.getColumnModel().getColumn(5).setPreferredWidth(57);
		table.getColumnModel().getColumn(6).setPreferredWidth(49);
		scrollPane.setViewportView(table);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/kiaora-s-icon.png")));
		lblLogo.setBounds(10, 11, 50, 50);
		panel.add(lblLogo);

		JLabel lblFlightRegistration = new JLabel("Flight Registration");
		lblFlightRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlightRegistration.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblFlightRegistration.setBounds(89, 11, 330, 50);
		panel.add(lblFlightRegistration);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(491, 87, 315, 408);
		panel.add(tabbedPane);

		panel_add = new JPanel();
		panel_add.setBackground(SystemColor.menu);
		tabbedPane.addTab("Add Flight", null, panel_add, null);
		tabbedPane.setBackgroundAt(0, UIManager.getColor("TabbedPane.background"));
		tabbedPane.setEnabledAt(0, true);
		panel_add.setLayout(null);

		JLabel lblOrigin = new JLabel("Origin:");
		lblOrigin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOrigin.setBounds(31, 38, 54, 24);
		panel_add.add(lblOrigin);

		JLabel lblDestination = new JLabel("Destination:");
		lblDestination.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDestination.setBounds(31, 73, 73, 19);
		panel_add.add(lblDestination);

		JLabel lblFlightDate = new JLabel("Flight Date:");
		lblFlightDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFlightDate.setBounds(31, 110, 73, 24);
		panel_add.add(lblFlightDate);

		JLabel lblFlightTime = new JLabel("Depart Time (24hr):");
		lblFlightTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFlightTime.setBounds(31, 146, 128, 24);
		panel_add.add(lblFlightTime);

		JLabel lblCapacity = new JLabel("Capacity:");
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCapacity.setBounds(31, 178, 73, 24);
		panel_add.add(lblCapacity);

		JLabel lblFare = new JLabel("Fare (NZ$):");
		lblFare.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFare.setBounds(31, 213, 73, 24);
		panel_add.add(lblFare);

		cbxAddOrigin = new JComboBox();
		cbxAddDest = new JComboBox();
		
		//Populating Origin and destination combo box with Airport codes from database
		try 
		{
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String s = "SELECT * FROM `iata_airport_codes` ";
			rs = st.executeQuery(s);
			while(rs.next())
			{
				cbxAddOrigin.addItem(rs.getString(2)+", "+ rs.getString(1));
				cbxAddDest.addItem(rs.getString(2)+", "+ rs.getString(1));
			}
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "ERROR");
		} finally{
			try {
				st.close();
				rs.close();
				conn.close();
			} catch (Exception ex){
				JOptionPane.showMessageDialog(null, "ERROR CLOSE");
			}
		}

		cbxAddOrigin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cbxAddOrigin.setBounds(109, 38, 180, 24);
		panel_add.add(cbxAddOrigin);

		cbxAddDest.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cbxAddDest.setBounds(109, 73, 180, 24);
		panel_add.add(cbxAddDest);

		JDateChooser dateChooserAdd = new JDateChooser();
		dateChooserAdd.setDateFormatString("yyyy-MM-dd");
		dateChooserAdd.setBounds(142, 110, 117, 24);
		panel_add.add(dateChooserAdd);

		JSpinner spinnerCapacityAdd = new JSpinner();
		spinnerCapacityAdd.setModel(new SpinnerNumberModel(30, 0, 30, 1));
		spinnerCapacityAdd.setBounds(190, 178, 67, 24);
		panel_add.add(spinnerCapacityAdd);

		txtAddFare = new JTextField();
		txtAddFare.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAddFare.setBounds(171, 213, 86, 24);
		panel_add.add(txtAddFare);
		txtAddFare.setColumns(10);

		JButton btnAddFlight = new JButton("Add Flight");
		btnAddFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Adding flight to database
				try 
				{
					//Setting given inputs to variable
					String o = cbxAddOrigin.getSelectedItem().toString();
					String origin = o.substring(0, 3);
					String d = cbxAddDest.getSelectedItem().toString();
					String dest = d.substring(0, 3);

					String date = ((JTextField)dateChooserAdd.getDateEditor().getUiComponent()).getText().toString();
					String time = frmtdtxtfldTimeAdd.getText().toString();

					// Checking if the text fields are empty or not
					if(time.equals("") || txtAddFare.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "All Fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Checking if Origin & Destination are same of not
					else if (origin.equals(dest))
					{
						JOptionPane.showMessageDialog(null, "Origin and Destination cannot be same!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
					{
						int capacity = Integer.parseInt(spinnerCapacityAdd.getValue().toString());
						double fare = Double.parseDouble(txtAddFare.getText().toString());
						String query = "INSERT INTO `flight`(`origin`, `destination`, `flightDate`, `departTime`, `capacity`, `fare`) VALUES ('"+origin+"','"+dest+"','"+date+"','"+time+"','"+capacity+"','"+fare+"')";
						executeSQLQuery(query, "Added!");
					}
				}
				catch (NumberFormatException n)
				{
					JOptionPane.showMessageDialog(null, "Fare must be in number", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});
		btnAddFlight.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/Add.png")));
		btnAddFlight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddFlight.setBounds(82, 270, 148, 45);
		panel_add.add(btnAddFlight);

		Format timeFormat = new SimpleDateFormat("HH:mm:ss");
		frmtdtxtfldTimeAdd = new JFormattedTextField(timeFormat);
		frmtdtxtfldTimeAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmtdtxtfldTimeAdd.setBounds(169, 150, 88, 20);
		panel_add.add(frmtdtxtfldTimeAdd);

		JLabel lblhhmmss = new JLabel("(hh:mm:ss)");
		lblhhmmss.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblhhmmss.setBounds(60, 166, 65, 14);
		panel_add.add(lblhhmmss);

		JPanel panel_search = new JPanel();
		panel_search.setBackground(SystemColor.menu);
		tabbedPane.addTab("Search & Update", null, panel_search, null);
		panel_search.setLayout(null);

		JLabel lblUpdateFlightID = new JLabel("Flight ID:");
		lblUpdateFlightID.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUpdateFlightID.setBounds(31, 144, 73, 24);
		panel_search.add(lblUpdateFlightID);

		txtFlightUpdateId = new JTextField();
		txtFlightUpdateId.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtFlightUpdateId.setEnabled(false);
		txtFlightUpdateId.setColumns(10);
		txtFlightUpdateId.setBounds(201, 144, 86, 19);
		panel_search.add(txtFlightUpdateId);

		JLabel lblUpdateOrigin = new JLabel("Origin:");
		lblUpdateOrigin.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUpdateOrigin.setBounds(31, 164, 54, 24);
		panel_search.add(lblUpdateOrigin);

		JLabel lblUpdateDestination = new JLabel("Destination:");
		lblUpdateDestination.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUpdateDestination.setBounds(31, 192, 73, 19);
		panel_search.add(lblUpdateDestination);

		JLabel lblUpdateDate = new JLabel("Flight Date:");
		lblUpdateDate.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUpdateDate.setBounds(31, 215, 73, 24);
		panel_search.add(lblUpdateDate);

		JLabel lblUpdateTime = new JLabel("Depart Time (24hr):");
		lblUpdateTime.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUpdateTime.setBounds(31, 240, 117, 24);
		panel_search.add(lblUpdateTime);

		JLabel lblUpdatetimeFormat = new JLabel("(hh:mm:ss)");
		lblUpdatetimeFormat.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblUpdatetimeFormat.setBounds(143, 249, 46, 10);
		panel_search.add(lblUpdatetimeFormat);

		frmtdtxtfldTimeUpdate = new JFormattedTextField(timeFormat);
		frmtdtxtfldTimeUpdate.setFont(new Font("Dialog", Font.PLAIN, 12));
		frmtdtxtfldTimeUpdate.setBounds(199, 241, 88, 20);
		panel_search.add(frmtdtxtfldTimeUpdate);

		spinnerCapacityUpdate = new JSpinner();
		spinnerCapacityUpdate.setModel(new SpinnerNumberModel(30, 0, 30, 1));
		spinnerCapacityUpdate.setFont(new Font("Dialog", Font.PLAIN, 12));
		spinnerCapacityUpdate.setBounds(220, 268, 67, 19);
		panel_search.add(spinnerCapacityUpdate);

		JLabel lblUpdateCapacity = new JLabel("Capacity:");
		lblUpdateCapacity.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUpdateCapacity.setBounds(31, 264, 73, 24);
		panel_search.add(lblUpdateCapacity);

		JLabel lblUpdateFare = new JLabel("Fare (NZ$):");
		lblUpdateFare.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUpdateFare.setBounds(31, 289, 73, 24);
		panel_search.add(lblUpdateFare);

		txtUpdateFare = new JTextField();
		txtUpdateFare.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtUpdateFare.setColumns(10);
		txtUpdateFare.setBounds(201, 294, 86, 19);
		panel_search.add(txtUpdateFare);

		JButton btnUpdateFlight = new JButton("Update");
		btnUpdateFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for updating flight data
				try 
				{
					//Setting given inputs to variable
					String origin = txtOriginUpdate.getText().toUpperCase();
					String dest = txtDestinationUpdate.getText().toUpperCase();
					String date = frmtdtxtfldDateUpdate.getText();
					String time = frmtdtxtfldTimeUpdate.getText();

					// Checking if the text fields are empty or not
					if(origin.equals("") || dest.equals("") || date.equals("") || time.equals("") || txtUpdateFare.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "All Fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Checking if Origin & Destination are 3 characters or not
					else if (!(origin.length() == 3) || !(dest.length() == 3))
					{
						JOptionPane.showMessageDialog(null, "Origin and Destination must be in 3 letter code", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Checking if Origin & Destination are same of not
					else if (origin.equals(dest))
					{
						JOptionPane.showMessageDialog(null, "Origin and Destination cannot be same!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
					{
						int capacity = Integer.parseInt(spinnerCapacityUpdate.getValue().toString());
						double fare = Double.parseDouble(txtUpdateFare.getText().toString());
						String query = "UPDATE `flight` SET `origin`='"+origin+"',`destination`='"+dest+"',`flightDate`='"+date+"',`departTime`='"+time+"' ,`capacity`='"+capacity+"',`fare`='"+fare+"' WHERE `flightID` = "+txtFlightUpdateId.getText()+"";
						executeSQLQuery(query, "Updated!");
					}
				}
				catch (NumberFormatException n)
				{
					JOptionPane.showMessageDialog(null, "Fare must be in number", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				catch (Exception exx)
				{
					exx.printStackTrace();
				}
			}
		});
		btnUpdateFlight.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/Modify.png")));
		btnUpdateFlight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUpdateFlight.setBounds(31, 324, 117, 45);
		panel_search.add(btnUpdateFlight);

		JButton btnDeleteFlight = new JButton("Delete");
		btnDeleteFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for deleting flight data
				try 
				{
					conn = DBUtil.getConnection(DBType.MYSQL);
					st = conn.createStatement();
					int count = 0;
					String s = "SELECT * FROM `ticket` WHERE CONCAT( `flightID`)LIKE '%"+txtFlightUpdateId.getText().toString()+"%'";
					rs = st.executeQuery(s);
					while(rs.next())
					{
						count = count + 1;
					}

					if (!(count == 0))
					{
						flightHasPassenger=true;
					} else
					{
						flightHasPassenger=false;
					}

				} catch (Exception e1)
				{
					e1.printStackTrace();
				} finally{
					try {
						st.close();
						rs.close();
						conn.close();
					} catch (Exception ex){
						JOptionPane.showMessageDialog(null, "ERROR CLOSE");
					}
				}

				if (txtFlightUpdateId.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please select a record from the table to delete!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if (flightHasPassenger == true)
				{
					JOptionPane.showMessageDialog(null, "You cannot delete this flight as it has passenger already registered to it!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					String query = "DELETE FROM `flight` WHERE `flightID` = "+txtFlightUpdateId.getText()+"";
					executeSQLQuery(query, "Deleted!");
				}
			}
		});
		btnDeleteFlight.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/Delete.png")));
		btnDeleteFlight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDeleteFlight.setBounds(170, 324, 117, 45);
		panel_search.add(btnDeleteFlight);

		JPanel pnlOD = new JPanel();
		pnlOD.setBorder(new LineBorder(new Color(128, 128, 128)));
		pnlOD.setBounds(8, 11, 147, 126);
		panel_search.add(pnlOD);
		pnlOD.setLayout(null);

		JLabel labelSO = new JLabel("Origin:");
		labelSO.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelSO.setBounds(10, 11, 54, 24);
		pnlOD.add(labelSO);

		JLabel labelSD = new JLabel("Destination:");
		labelSD.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelSD.setBounds(10, 39, 73, 19);
		pnlOD.add(labelSD);

		JButton btnSearchByOd = new JButton("");
		btnSearchByOd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button Action for searching flight data by origin & destination
				findFlightByOD();
			}
		});
		btnSearchByOd.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/Search.png")));
		btnSearchByOd.setBounds(45, 69, 54, 46);
		pnlOD.add(btnSearchByOd);

		txtSearchByO = new JTextField();
		txtSearchByO.setBounds(82, 14, 44, 20);
		pnlOD.add(txtSearchByO);
		txtSearchByO.setColumns(10);

		txtSearchByD = new JTextField();
		txtSearchByD.setBounds(82, 39, 44, 20);
		pnlOD.add(txtSearchByD);
		txtSearchByD.setColumns(10);

		JPanel pnlDD = new JPanel();
		pnlDD.setBorder(new LineBorder(new Color(128, 128, 128)));
		pnlDD.setBounds(154, 11, 147, 126);
		panel_search.add(pnlDD);
		pnlDD.setLayout(null);

		JLabel labelSDD = new JLabel("Destination:");
		labelSDD.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelSDD.setBounds(10, 40, 73, 19);
		pnlDD.add(labelSDD);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblDate.setBounds(10, 11, 37, 24);
		pnlDD.add(lblDate);

		JButton btnSearchByDd = new JButton("");
		btnSearchByDd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for searching flight data by date & destination
				findFlightByDD();
			}
		});
		btnSearchByDd.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/Search.png")));
		btnSearchByDd.setBounds(41, 70, 54, 45);
		pnlDD.add(btnSearchByDd);

		dateChooserSearch = new JDateChooser();
		dateChooserSearch.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 10));
		dateChooserSearch.setDateFormatString("yyyy-MM-dd");
		dateChooserSearch.setBounds(41, 15, 96, 20);
		pnlDD.add(dateChooserSearch);

		txtSearchByDd = new JTextField();
		txtSearchByDd.setBounds(93, 40, 44, 20);
		pnlDD.add(txtSearchByDd);
		txtSearchByDd.setColumns(10);

		txtOriginUpdate = new JTextField();
		txtOriginUpdate.setBounds(201, 167, 86, 20);
		panel_search.add(txtOriginUpdate);
		txtOriginUpdate.setColumns(10);

		txtDestinationUpdate = new JTextField();
		txtDestinationUpdate.setBounds(201, 191, 86, 20);
		panel_search.add(txtDestinationUpdate);
		txtDestinationUpdate.setColumns(10);

		Format dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		frmtdtxtfldDateUpdate = new JFormattedTextField(dateFormat);
		frmtdtxtfldDateUpdate.setBounds(201, 218, 86, 20);
		panel_search.add(frmtdtxtfldDateUpdate);
		tabbedPane.setEnabledAt(1, true);

		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for returning to Main Nav Frame
				dispose();
			}
		});
		button.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/Back.png")));
		button.setBounds(690, 20, 116, 41);
		panel.add(button);

		JButton btnExportRecord = new JButton("Export Record");
		btnExportRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for exporting flight data to excel file
				if (table.getModel().getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Nothing to save!");
				}
				else 
				{
					try{
						ExcelExporter exp = new ExcelExporter();
						exp.exportTable(table, new File ("D:/FlightRecords.xls"));
					} catch (IOException ee){
						ee.getMessage();
					}
				}
			}
		});
		btnExportRecord.setIcon(new ImageIcon(FlightFrame.class.getResource("/img/Save.png")));
		btnExportRecord.setBounds(512, 20, 158, 41);
		panel.add(btnExportRecord);

	}

	// Arraylist for showing all Flight records
	public static ArrayList<flight> flightList()
	{
		ArrayList<flight> flightList = new ArrayList<flight>();

		Statement st;
		ResultSet rs;

		try {
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String searchQuery = "SELECT * FROM `flight` ";
			rs = st.executeQuery(searchQuery);

			flight f;

			while(rs.next())
			{
				f = new flight (
						rs.getInt("flightID"),
						rs.getString("origin"),
						rs.getString("destination"),
						rs.getDate("flightDate"),
						rs.getTime("departTime"),
						rs.getInt("capacity"),
						rs.getDouble("fare")
						);
				flightList.add(f);
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
		return flightList;
	}


	//Display data in JTable
	public void ShowUsersInTable(){
		ArrayList<flight> list = flightList();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] {"Flight ID", "Origin", "Destination", "Date", "Time", "Capacity", "Fare"});

		Object[] row = new Object[7];

		for(int i = 0; i < list.size(); i++)
		{
			row[0]= list.get(i).getFlightId();
			row[1]= list.get(i).getOrigin();
			row[2]= list.get(i).getDestination();
			row[3]= list.get(i).getFlightDate();
			row[4]= list.get(i).getDepartTime();
			row[5]= list.get(i).getCapacity();
			row[6]= list.get(i).getFare();

			model.addRow(row);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(51);
		table.getColumnModel().getColumn(1).setPreferredWidth(47);
		table.getColumnModel().getColumn(2).setPreferredWidth(65);
		table.getColumnModel().getColumn(3).setPreferredWidth(67);
		table.getColumnModel().getColumn(4).setPreferredWidth(56);
		table.getColumnModel().getColumn(5).setPreferredWidth(57);
		table.getColumnModel().getColumn(6).setPreferredWidth(49);
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

	// Arraylist for searching all Flight records by Origin and Destination
	public static ArrayList<flight> ListByOD (String origin, String dest)
	{
		ArrayList<flight> flightList = new ArrayList<flight>();

		Statement st;
		ResultSet rs;

		try 
		{
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String searchOrigin = "SELECT * FROM `flight` WHERE CONCAT( `origin`)LIKE '%"+origin+"%' AND CONCAT( `destination`)LIKE '%"+dest+"%'";
			rs = st.executeQuery(searchOrigin);

			flight flight;

			while (rs.next())
			{
				flight = new flight(
						rs.getInt("flightID"),
						rs.getString("origin"),
						rs.getString("destination"),
						rs.getDate("flightDate"),
						rs.getTime("departTime"),
						rs.getInt("capacity"),
						rs.getDouble("fare")
						);
				flightList.add(flight);
			}
		}
		catch (Exception ex)
		{
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
		return flightList;
	}

	//Display Filtered data from input for origin & destination in JTable
	public static void findFlightByOD() {
		String origin = txtSearchByO.getText();
		String destination = txtSearchByD.getText();

		try
		{
			ArrayList<flight> flight = ListByOD(origin, destination);
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(new Object[] {"Flight ID", "Origin", "Destination", "Date", "Time", "Capacity", "Fare"});

			Object[] row = new Object[7];

			for(int i = 0; i < flight.size(); i++)
			{
				row[0]= flight.get(i).getFlightId();
				row[1]= flight.get(i).getOrigin();
				row[2]= flight.get(i).getDestination();
				row[3]= flight.get(i).getFlightDate();
				row[4]= flight.get(i).getDepartTime();
				row[5]= flight.get(i).getCapacity();
				row[6]= flight.get(i).getFare();

				model.addRow(row);
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(51);
			table.getColumnModel().getColumn(1).setPreferredWidth(47);
			table.getColumnModel().getColumn(2).setPreferredWidth(65);
			table.getColumnModel().getColumn(3).setPreferredWidth(67);
			table.getColumnModel().getColumn(4).setPreferredWidth(56);
			table.getColumnModel().getColumn(5).setPreferredWidth(57);
			table.getColumnModel().getColumn(6).setPreferredWidth(49);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Arraylist for searching all Flight records by Date and Destination
	public static ArrayList<flight> ListByDD (String date, String dest)
	{
		ArrayList<flight> flightList = new ArrayList<flight>();

		Statement st;
		ResultSet rs;

		try 
		{
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String searchOrigin = "SELECT * FROM `flight` WHERE CONCAT( `flightDate`)LIKE '%"+date+"%' AND CONCAT( `destination`)LIKE '%"+dest+"%'";
			rs = st.executeQuery(searchOrigin);

			flight flight;

			while (rs.next())
			{
				flight = new flight(
						rs.getInt("flightID"),
						rs.getString("origin"),
						rs.getString("destination"),
						rs.getDate("flightDate"),
						rs.getTime("departTime"),
						rs.getInt("capacity"),
						rs.getDouble("fare")
						);
				flightList.add(flight);
			}
		}
		catch (Exception ex)
		{
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
		return flightList;
	}

	//Display Filtered data from input for date & destination in JTable
	public static void findFlightByDD() {
		String date = ((JTextField)dateChooserSearch.getDateEditor().getUiComponent()).getText().toString();
		String destination = txtSearchByDd.getText();

		try
		{
			ArrayList<flight> flight = ListByDD(date, destination);
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(new Object[] {"Flight ID", "Origin", "Destination", "Date", "Time", "Capacity", "Fare"});

			Object[] row = new Object[7];

			for(int i = 0; i < flight.size(); i++)
			{
				row[0]= flight.get(i).getFlightId();
				row[1]= flight.get(i).getOrigin();
				row[2]= flight.get(i).getDestination();
				row[3]= flight.get(i).getFlightDate();
				row[4]= flight.get(i).getDepartTime();
				row[5]= flight.get(i).getCapacity();
				row[6]= flight.get(i).getFare();

				model.addRow(row);
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(51);
			table.getColumnModel().getColumn(1).setPreferredWidth(47);
			table.getColumnModel().getColumn(2).setPreferredWidth(65);
			table.getColumnModel().getColumn(3).setPreferredWidth(67);
			table.getColumnModel().getColumn(4).setPreferredWidth(56);
			table.getColumnModel().getColumn(5).setPreferredWidth(57);
			table.getColumnModel().getColumn(6).setPreferredWidth(49);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void selectSearch(){
		tabbedPane.setSelectedIndex(1);
	}
}
