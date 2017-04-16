package db;
/*
 * 
 *  ExcelExporter class
 *  Author: Kapil 
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ExcelExporter {
	
	public ExcelExporter() {
	}

	// Method to export table data to Excel file
	public void exportTable(JTable table, File file) throws IOException {
		TableModel model = table.getModel();
		FileWriter out = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(out);
		for (int i = 0; i < model.getColumnCount(); i++)
		{
			bw.write(model.getColumnName(i) + "\t");
		}
		bw.write("\n");
		
		for (int i = 0; i < model.getRowCount(); i++){
			for (int j =0; j< model.getColumnCount(); j++){
				bw.write(model.getValueAt(i, j).toString()+"\t");
			}
			bw.write("\n");
		}
		bw.close();
		JOptionPane.showMessageDialog(null, "Report saved to " + file);
	}
}
