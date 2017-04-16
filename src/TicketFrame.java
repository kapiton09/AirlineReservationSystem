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
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import beans.ticket;
import db.DBType;
import db.DBUtil;
import db.ExcelExporter;
import db.PrintPreview;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.*;
import java.text.MessageFormat;
import javax.print.attribute.*;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import javax.swing.JTable.PrintMode;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
/*
 * 
 *  TicketFrame class
 *  Author: Kapil 
 * 
 */
public class TicketFrame extends JFrame {

	private JPanel contentPane;
	private static JTable table;
	private JTextField txtTicketId;
	private JTextField txtFlightDate;
	private JTextField txtFlightCost;
	private JTextField txtAvailability;
	private JComboBox cbxPassenger;
	private JComboBox cbxFlight;
	static Connection conn;
	Statement st;
	ResultSet rs;
	private JComboBox cbxSeatRow;
	private JComboBox cbxSeatLabel;
	private boolean seatAvailability = true;
	private static JTabbedPane tabbedPane;
	private static JTable tableReport;
	
	 PrinterJob printerJob = PrinterJob.getPrinterJob();
	 HashPrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
	 
	/**
	 * Launch the application.
	 */

			public void run() {
				try {
					TicketFrame frame = new TicketFrame();
					frame.setVisible(true);
					ShowUsersInTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	/**
	 * Create the frame.
	 */
	public TicketFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TicketFrame.class.getResource("/img/kiaora-s-icon.png")));
		setTitle("Ticket Booking");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 960, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(TicketFrame.class.getResource("/img/kiaora-s-icon.png")));
		lblLogo.setBounds(10, 11, 50, 50);
		contentPane.add(lblLogo);

		JLabel lblTitle = new JLabel("Ticket Booking");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTitle.setBounds(89, 11, 330, 50);
		contentPane.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 82, 527, 413);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ticket ID", "Passenger ID", "Flight ID", "Flight Date", "Cost", "Booking Date", "Seat Row", "Seat Label"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(53);
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		table.getColumnModel().getColumn(2).setPreferredWidth(53);
		table.getColumnModel().getColumn(4).setPreferredWidth(49);
		table.getColumnModel().getColumn(5).setPreferredWidth(84);
		table.getColumnModel().getColumn(6).setPreferredWidth(56);
		table.getColumnModel().getColumn(7).setPreferredWidth(63);
		scrollPane.setViewportView(table);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(557, 59, 375, 436);
		contentPane.add(tabbedPane);

		JPanel panelBook = new JPanel();
		tabbedPane.addTab("Book Ticket", null, panelBook, null);
		panelBook.setLayout(null);

		JLabel lblTicketId = new JLabel("Ticket ID: ");
		lblTicketId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTicketId.setBounds(33, 28, 74, 22);
		panelBook.add(lblTicketId);

		JLabel lblPassenger = new JLabel("Passenger:");
		lblPassenger.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassenger.setBounds(33, 58, 74, 22);
		panelBook.add(lblPassenger);

		JLabel lblFlight = new JLabel("Flight:");
		lblFlight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFlight.setBounds(33, 91, 74, 22);
		panelBook.add(lblFlight);

		JLabel lblFlightDate = new JLabel("Flight Date:");
		lblFlightDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFlightDate.setBounds(33, 157, 74, 22);
		panelBook.add(lblFlightDate);

		JLabel lblFlightCost = new JLabel("Flight Cost:");
		lblFlightCost.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFlightCost.setBounds(33, 190, 74, 22);
		panelBook.add(lblFlightCost);

		JLabel lblBookingDate = new JLabel("Booking Date:");
		lblBookingDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBookingDate.setBounds(33, 223, 88, 22);
		panelBook.add(lblBookingDate);

		JLabel lblSeatRow = new JLabel("Seat Row:");
		lblSeatRow.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeatRow.setBounds(33, 256, 74, 22);
		panelBook.add(lblSeatRow);

		JLabel lblSeatLabel = new JLabel("Seat Label:");
		lblSeatLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeatLabel.setBounds(33, 289, 74, 22);
		panelBook.add(lblSeatLabel);

		txtTicketId = new JTextField();
		txtTicketId.setEnabled(false);
		txtTicketId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTicketId.setBounds(131, 28, 86, 22);
		panelBook.add(txtTicketId);
		txtTicketId.setColumns(10);

		cbxPassenger = new JComboBox();
		cbxPassenger.setModel(new DefaultComboBoxModel(new String[] {""}));
		cbxPassenger.setSelectedIndex(0);
		
		//Extracting Passenger data from database and populating ComboBox
		try 
		{
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String s = "SELECT * FROM `passenger` ";
			rs = st.executeQuery(s);
			while(rs.next())
			{
				cbxPassenger.addItem(rs.getString(1)+" -> "+ rs.getString(3)+" "+ rs.getString(4));
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

		cbxPassenger.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxPassenger.setBounds(131, 60, 213, 20);
		panelBook.add(cbxPassenger);

		cbxFlight = new JComboBox();
		cbxFlight.setModel(new DefaultComboBoxModel(new String[] {""}));
		cbxFlight.setSelectedIndex(0);
		
		//Extracting Flight data from database and populating Combobox
		try 
		{
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String s = "SELECT * FROM `flight` ";
			rs = st.executeQuery(s);
			while(rs.next())
			{
				cbxFlight.addItem(rs.getString(1)+" -> "+ rs.getString(2)+" to "+ rs.getString(3));
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

		cbxFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		cbxFlight.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String flightID = cbxFlight.getSelectedItem().toString().substring(0, 3);
				//Extracting Flight availability, date and cost from selected Flight from Combobox
				try 
				{
					conn = DBUtil.getConnection(DBType.MYSQL);
					st = conn.createStatement();
					String s = "SELECT * FROM `flight` WHERE CONCAT( `flightID`)LIKE '%"+flightID+"%'";
					rs = st.executeQuery(s);
					while(rs.next())
					{
						txtAvailability.setText(rs.getString(6));
						txtFlightDate.setText(rs.getString(4));
						txtFlightCost.setText(rs.getString(7));
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally{
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



		cbxFlight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxFlight.setBounds(131, 93, 213, 20);
		panelBook.add(cbxFlight);

		txtFlightDate = new JTextField();
		txtFlightDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtFlightDate.setEnabled(false);
		txtFlightDate.setColumns(10);
		txtFlightDate.setBounds(131, 159, 86, 22);
		panelBook.add(txtFlightDate);

		txtFlightCost = new JTextField();
		txtFlightCost.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtFlightCost.setEnabled(false);
		txtFlightCost.setColumns(10);
		txtFlightCost.setBounds(131, 192, 86, 22);
		panelBook.add(txtFlightCost);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(131, 223, 119, 20);
		panelBook.add(dateChooser);

		JLabel lblAvaibility = new JLabel("Availability:");
		lblAvaibility.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAvaibility.setBounds(33, 124, 74, 22);
		panelBook.add(lblAvaibility);

		txtAvailability = new JTextField();
		txtAvailability.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAvailability.setEnabled(false);
		txtAvailability.setColumns(10);
		txtAvailability.setBounds(131, 126, 44, 22);
		panelBook.add(txtAvailability);

		JButton btnBookTicket = new JButton("Book Ticket");
		btnBookTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Adding Ticket to database
				try 
				{
					//Setting given inputs to variable
					String p = cbxPassenger.getSelectedItem().toString();
					String f = cbxFlight.getSelectedItem().toString();
					String flightDate = txtFlightDate.getText();
					String flightCost = txtFlightCost.getText();
					String bookingDate = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText().toString();
					String seatRow = cbxSeatRow.getSelectedItem().toString();
					String seatLabel = cbxSeatLabel.getSelectedItem().toString();

					// Checking if the text fields are empty or not
					if(cbxPassenger.getSelectedIndex() == 0 || cbxFlight.getSelectedIndex() == 0 || flightDate.equals("") || flightCost.equals("") || bookingDate.equals("") || seatRow.equals("") || seatLabel.equals(""))
					{
						JOptionPane.showMessageDialog(null, "All Fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Checking Seat Availability
					int capacity = Integer.parseInt(txtAvailability.getText().toString());
					String passengerID = p.substring(0, 3);
					String flightID = f.substring(0, 3);
					try 
					{
						conn = DBUtil.getConnection(DBType.MYSQL);
						st = conn.createStatement();
						int count = 1;
						String fid;
						String s = "SELECT * FROM `ticket` WHERE CONCAT( `seatRow`)LIKE '%"+seatRow+"%'  AND CONCAT( `seatLabel`)LIKE '%"+seatLabel+"%'";
						rs = st.executeQuery(s);
						while(rs.next())
						{
							fid = rs.getString(3);
							if (fid.equals(flightID)){
								seatAvailability=false;
								count = count + 1;
							} else
							{
								seatAvailability=true;
							}
							
						}

						if (!(count == 1))
						{
							seatAvailability=false;
						} else
						{
							seatAvailability=true;
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
					
					

					// Checking if Flight has enough capacity or not
					if (capacity <= 0)
					{
						JOptionPane.showMessageDialog(null, "This flight is Full! Please Check another one.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					else if (seatAvailability == false)
					{
						JOptionPane.showMessageDialog(null, "Seat not available! Please choose another Seat. ", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
					{
						String Insertquery = "INSERT INTO `ticket`(`passengerID`, `flightID`, `flightDate`, `cost`, `bookingDate`, `seatRow`, `seatLabel`) VALUES ('"+passengerID+"','"+flightID+"','"+flightDate+"','"+flightCost+"','"+bookingDate+"','"+seatRow+"','"+seatLabel+"')";
						executeSQLQuery(Insertquery, "Added!");
						// Deducting a capacity from the Flight table
						int newCapacity = capacity - 1;
						txtAvailability.setText(newCapacity+"");
						String Updatequery = "UPDATE `flight` SET `capacity`='"+newCapacity+"' WHERE `flightID` = "+flightID+"";
						executeSQLQuery(Updatequery, "Flight Availability Updated!");
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		btnBookTicket.setIcon(new ImageIcon(TicketFrame.class.getResource("/img/Save.png")));
		btnBookTicket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBookTicket.setBounds(116, 337, 148, 40);
		panelBook.add(btnBookTicket);

		cbxSeatRow = new JComboBox();
		cbxSeatRow.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cbxSeatRow.setSelectedIndex(0);
		cbxSeatRow.setBounds(131, 258, 44, 20);
		panelBook.add(cbxSeatRow);

		cbxSeatLabel = new JComboBox();
		cbxSeatLabel.setModel(new DefaultComboBoxModel(new String[] {"", "A", "B", "C"}));
		cbxSeatLabel.setSelectedIndex(0);
		cbxSeatLabel.setBounds(131, 291, 44, 20);
		panelBook.add(cbxSeatLabel);

		JPanel panelReport = new JPanel();
		tabbedPane.addTab("Report", null, panelReport, null);
		panelReport.setLayout(null);
		
		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Generating report for total revenue from the table
				try 
				{
					conn = DBUtil.getConnection(DBType.MYSQL);
					st = conn.createStatement();
					int count = 0;
					double revenue = 0;
					DecimalFormat df2 = new DecimalFormat(".##");
					double average = 0;
					String s = "SELECT * FROM `ticket`";
					rs = st.executeQuery(s);
					while(rs.next())
					{
						count = count + 1;
						revenue = revenue + rs.getDouble(5);
					}
					average = revenue / count;
					
					DefaultTableModel model = new DefaultTableModel();
					model.setColumnIdentifiers(new Object[] {"No. of Bookings", "Total Revenue (NZ$)", "Average Fare (NZ$)"});
					model.addRow(new Object[]{count, revenue, df2.format(average)});
					tableReport.setModel(model);
					tableReport.getColumnModel().getColumn(0).setPreferredWidth(90);
					tableReport.getColumnModel().getColumn(1).setPreferredWidth(114);
					tableReport.getColumnModel().getColumn(2).setPreferredWidth(113);
					
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				finally
				{
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
		btnGenerateReport.setIcon(new ImageIcon(TicketFrame.class.getResource("/img/Modify.png")));
		btnGenerateReport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGenerateReport.setBounds(78, 63, 214, 46);
		panelReport.add(btnGenerateReport);
		
		JPanel pnlReport = new JPanel();
		pnlReport.setBounds(10, 120, 350, 261);
		panelReport.add(pnlReport);
		pnlReport.setLayout(null);
		
		JScrollPane scrollPane_Report = new JScrollPane();
		scrollPane_Report.setBounds(10, 11, 330, 98);
		pnlReport.add(scrollPane_Report);
		
		tableReport = new JTable();
		tableReport.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No. of Bookings", "Total Revenue (NZ$)", "Average Fare (NZ$)"
			}
		));
		tableReport.getColumnModel().getColumn(0).setPreferredWidth(90);
		tableReport.getColumnModel().getColumn(1).setPreferredWidth(114);
		tableReport.getColumnModel().getColumn(2).setPreferredWidth(113);
		scrollPane_Report.setViewportView(tableReport);
		
		JButton btnPrintReport = new JButton("Save Report");
		btnPrintReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Exporting total revenue table data to Excel file
				if (tableReport.getModel().getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Nothing to save! Generate Report First.");
				}
				else 
				{
					try{
						ExcelExporter exp = new ExcelExporter();
						exp.exportTable(tableReport, new File ("D:/TotalRevenue.xls"));
					} catch (IOException ee){
						ee.getMessage();
					}
				}
			}
		});
		btnPrintReport.setIcon(new ImageIcon(TicketFrame.class.getResource("/img/Save.png")));
		btnPrintReport.setBounds(84, 130, 179, 42);
		pnlReport.add(btnPrintReport);
		
		JButton btnPrintReport_1 = new JButton("Print Report");
		btnPrintReport_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Printing total revenue report
				if (tableReport.getModel().getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Nothing to Print! Generate Report First.");
				}
				else
				{
					attributes.add(new MediaPrintableArea(6, 6, 198, 278, MediaPrintableArea.MM)); // A4: 210x297mm
		            attributes.add(Sides.DUPLEX);
		            attributes.add(OrientationRequested.LANDSCAPE);
		            new PrintPreview(
		                tableReport.getPrintable(PrintMode.FIT_WIDTH, null, new MessageFormat("Page {0}") ), 
		                printerJob.getPageFormat(attributes)
		            );
				}
			}
		});
		btnPrintReport_1.setIcon(new ImageIcon(TicketFrame.class.getResource("/img/Print.png")));
		btnPrintReport_1.setBounds(84, 183, 179, 42);
		pnlReport.add(btnPrintReport_1);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnReturn.setIcon(new ImageIcon(TicketFrame.class.getResource("/img/Back.png")));
		btnReturn.setBounds(821, 11, 111, 37);
		contentPane.add(btnReturn);
		
		JButton btnExportRecords = new JButton("Export Records");
		btnExportRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Exporting ticket data from table to excel file
				if (table.getModel().getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Nothing to save!");
				}
				else 
				{
					try{
						ExcelExporter exp = new ExcelExporter();
						exp.exportTable(table, new File ("D:/TicketRecords.xls"));
					} catch (IOException ee){
						ee.getMessage();
					}
				}
			}
		});
		btnExportRecords.setIcon(new ImageIcon(TicketFrame.class.getResource("/img/Load.png")));
		btnExportRecords.setBounds(625, 11, 174, 37);
		contentPane.add(btnExportRecords);
	}

	// Arraylist for showing all Ticket records
	public static ArrayList<ticket> ticketList()
	{
		ArrayList<ticket> ticketList = new ArrayList<ticket>();
		Statement st;
		ResultSet rs;

		try {
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String searchQuery = "SELECT * FROM `ticket` ";
			rs = st.executeQuery(searchQuery);

			ticket t;

			while(rs.next())
			{
				t = new ticket (
						rs.getInt("ticketID"),
						rs.getInt("passengerID"),
						rs.getInt("flightID"),
						rs.getDate("flightDate"),
						rs.getDouble("cost"),
						rs.getDate("bookingDate"),
						rs.getInt("seatRow"),
						rs.getString("seatLabel")
						);
				ticketList.add(t);
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
		return ticketList;
	}

	//Display data in JTable
	public void ShowUsersInTable(){
		ArrayList<ticket> list = ticketList();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] {"Ticket ID", "Passenger ID", "Flight ID", "Flight Date", "Cost", "Booking Date", "Seat Row", "Seat Label"});

		Object[] row = new Object[8];

		for(int i = 0; i < list.size(); i++)
		{
			row[0]= list.get(i).getTicketID();
			row[1]= list.get(i).getPassengerID();
			row[2]= list.get(i).getFlightID();
			row[3]= list.get(i).getFlightDate();
			row[4]= list.get(i).getCost();
			row[5]= list.get(i).getBookingDate();
			row[6]= list.get(i).getSeatRow();
			row[7]= list.get(i).getSeatLabel();

			model.addRow(row);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(53);
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		table.getColumnModel().getColumn(2).setPreferredWidth(53);
		table.getColumnModel().getColumn(4).setPreferredWidth(49);
		table.getColumnModel().getColumn(5).setPreferredWidth(84);
		table.getColumnModel().getColumn(6).setPreferredWidth(56);
		table.getColumnModel().getColumn(7).setPreferredWidth(63);
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
	
	public void selectReport(){
		tabbedPane.setSelectedIndex(1);
	}
}
