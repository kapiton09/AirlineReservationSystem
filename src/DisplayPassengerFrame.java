import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable.PrintMode;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import beans.passenger;
import db.DBType;
import db.DBUtil;
import db.ExcelExporter;
import db.PrintPreview;

import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.print.*;
import java.text.MessageFormat;
import javax.print.attribute.*;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import javax.swing.JTable.PrintMode;
/*
 * 
 *  DisplayPassengerFrame class
 *  Author: Kapil 
 * 
 */
public class DisplayPassengerFrame extends JFrame {

	private JPanel contentPane;
	private static JTable table;
	private JComboBox cbxFlight;
	private String pID;
	static Connection conn;
	Statement st;
	ResultSet rs;
	
	PrinterJob printerJob = PrinterJob.getPrinterJob();
	 HashPrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
	 
	/**
	 * Launch the application.
	 */
	public void run() {
		try {
			DisplayPassengerFrame frame = new DisplayPassengerFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	public DisplayPassengerFrame() {
		setTitle("Display Passenger by a Flight");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DisplayPassengerFrame.class.getResource("/img/kiaora-s-icon.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 684, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(DisplayPassengerFrame.class.getResource("/img/kiaora-s-icon.png")));
		label.setBounds(10, 11, 50, 50);
		contentPane.add(label);

		JLabel lblDisplayPassengerBy = new JLabel("Display Passenger by Flight");
		lblDisplayPassengerBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayPassengerBy.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblDisplayPassengerBy.setBounds(100, 11, 366, 50);
		contentPane.add(lblDisplayPassengerBy);

		cbxFlight = new JComboBox();
		cbxFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Combo box Action for populating table with passenger records from selected flight
				if (cbxFlight.getSelectedIndex() == 0){
					
				} else 
				{
					try 
					{
						// Getting selected flight ID
						String flightID = cbxFlight.getSelectedItem().toString().substring(0, 3);
						conn = DBUtil.getConnection(DBType.MYSQL);
						st = conn.createStatement();
						// Filtering Passenger from selected flightID in the Ticket Table
						String s = "SELECT passengerID FROM `ticket` WHERE CONCAT( `flightID`)LIKE '%"+flightID+"%'";
						rs = st.executeQuery(s);
						int fCount = 0;
						DefaultTableModel model = new DefaultTableModel();
						model.setColumnIdentifiers(new Object[] {"ID", "Passport No", "First Name", "Last Name", "Email"});
						while(rs.next())
						{
							pID = rs.getString(1);
							fCount = fCount + 1;
							//Display Filtered data from input for Last Name in JTable
							ArrayList<passenger> pass = ListByPassengerID(pID);
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
							table.getColumnModel().getColumn(0).setPreferredWidth(84);
							table.getColumnModel().getColumn(1).setPreferredWidth(77);
							table.getColumnModel().getColumn(2).setPreferredWidth(84);
							table.getColumnModel().getColumn(3).setPreferredWidth(92);
							table.getColumnModel().getColumn(4).setPreferredWidth(202);
						}
						
						if (fCount == 0){
							JOptionPane.showMessageDialog(null, "No Passenger found for this flight!");
							table.setModel(new DefaultTableModel(
									new Object[][] {
									},
									new String[] {
										"Passenger ID", "Passport No", "First Name", "Last Name", "Email"
									}
								));
								table.getColumnModel().getColumn(0).setPreferredWidth(84);
								table.getColumnModel().getColumn(1).setPreferredWidth(77);
								table.getColumnModel().getColumn(2).setPreferredWidth(84);
								table.getColumnModel().getColumn(3).setPreferredWidth(92);
								table.getColumnModel().getColumn(4).setPreferredWidth(202);
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
				}
			}
		});
		cbxFlight.setModel(new DefaultComboBoxModel(new String[] {""}));
		cbxFlight.setSelectedIndex(0);

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


		cbxFlight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxFlight.setBounds(74, 98, 184, 28);
		contentPane.add(cbxFlight);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 144, 598, 256);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Passenger ID", "Passport No", "First Name", "Last Name", "Email"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(84);
		table.getColumnModel().getColumn(1).setPreferredWidth(77);
		table.getColumnModel().getColumn(2).setPreferredWidth(84);
		table.getColumnModel().getColumn(3).setPreferredWidth(92);
		table.getColumnModel().getColumn(4).setPreferredWidth(202);
		scrollPane.setViewportView(table);

		JLabel lblFlight = new JLabel("Flight:");
		lblFlight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFlight.setBounds(31, 98, 46, 28);
		contentPane.add(lblFlight);

		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button Action for returning to Main Nav
				dispose();
			}
		});
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReturn.setIcon(new ImageIcon(DisplayPassengerFrame.class.getResource("/img/Back.png")));
		btnReturn.setBounds(538, 26, 120, 35);
		contentPane.add(btnReturn);

		JButton btnExportReport = new JButton("Export Report");
		btnExportReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Button Action for exporting table data to Excel file
				if (cbxFlight.getSelectedIndex() == 0)
				{
					JOptionPane.showMessageDialog(null, "Please select Flight!");
				} else if (table.getModel().getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Please select flight which has Passenger!");
				}
				else 
				{
					try{
						String flightID = cbxFlight.getSelectedItem().toString().substring(0, 3);
						ExcelExporter exp = new ExcelExporter();
						exp.exportTable(table, new File ("D:/PassengerRecordsForFlight"+flightID+".xls"));
					} catch (IOException e){
						e.getMessage();
					}
				}
			}
		});
		btnExportReport.setIcon(new ImageIcon(DisplayPassengerFrame.class.getResource("/img/Save.png")));
		btnExportReport.setBounds(268, 91, 158, 45);
		contentPane.add(btnExportReport);
		
		JButton btnPrintReport = new JButton("Print Report");
		btnPrintReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Button Action for printing table data
				if (table.getModel().getRowCount() == 0)
				{
					JOptionPane.showMessageDialog(null, "Nothing to Print! Generate Report First.");
				}
				else
				{
					attributes.add(new MediaPrintableArea(6, 6, 198, 278, MediaPrintableArea.MM)); // A4: 210x297mm
		            attributes.add(Sides.DUPLEX);
		            attributes.add(OrientationRequested.LANDSCAPE);
		            new PrintPreview(
		                table.getPrintable(PrintMode.FIT_WIDTH, null, new MessageFormat("Page {0}") ), 
		                printerJob.getPageFormat(attributes)
		            );
				}
			}
		});
		btnPrintReport.setIcon(new ImageIcon(DisplayPassengerFrame.class.getResource("/img/Print.png")));
		btnPrintReport.setBounds(436, 91, 176, 42);
		contentPane.add(btnPrintReport);

	}

	// Arraylist for searching all Passenger records by Last Name
	public static ArrayList<passenger> ListByPassengerID(String pID)
	{
		ArrayList<passenger> usersList = new ArrayList<passenger>();

		Statement st;
		ResultSet rs;

		try {
			conn = DBUtil.getConnection(DBType.MYSQL);
			st = conn.createStatement();
			String searchQuery = "SELECT * FROM `passenger` WHERE CONCAT( `passengerID`)LIKE '%"+pID+"%'";
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

}
