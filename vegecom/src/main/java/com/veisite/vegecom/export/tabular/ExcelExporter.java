package com.veisite.vegecom.export.tabular;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelExporter {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelExporter.class);

	public static File saveTableModelToFile(TableModel model, File file) 
			throws IOException, WriteException {
		
		// Construir la hoja xls
		WritableWorkbook workbook = Workbook.createWorkbook(file); 
		WritableSheet sheet = workbook.createSheet("Hoja 1", 0);

		int columns = model.getColumnCount();
		// Escribir las cabeceras
		for (int i=0;i<columns;i++) {
			Label label = new Label(i, 0, model.getColumnName(i));
			sheet.addCell(label); 
		}
		// Escribir los datos
		for (int c=0;c<columns;c++) 
			for (int r=0;r<model.getRowCount();r++)
				addCell(sheet, c,r+1,model.getValueAt(r, c));
		
		workbook.write();
		workbook.close(); 
		return file;
	}

	private static void addCell(WritableSheet sheet, int column, int row, Object value) throws WriteException {
		try {
			if (value instanceof Date) {
				sheet.addCell(new DateTime(column, row, (Date) value, DateTime.GMT));
			} else if (value instanceof Calendar) {
				sheet.addCell(new DateTime(column, row, ((Calendar)value).getTime(), DateTime.GMT));
			} else if (value instanceof Number) {
				sheet.addCell(new jxl.write.Number(column, row, ((Number)value).doubleValue()));
			} else if (value instanceof Boolean) {
				sheet.addCell(new jxl.write.Boolean(column, row, ((Boolean)value).booleanValue()));
			} else {
				sheet.addCell(new Label(column, row, value.toString()));
			}
		} catch (RowsExceededException ree) {
			logger.error("Error al añadir celda.",ree);
		}
	}

}
