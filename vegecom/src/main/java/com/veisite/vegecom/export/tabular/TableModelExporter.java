package com.veisite.vegecom.export.tabular;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.table.TableModel;

import jxl.write.WriteException;

import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableModelExporter {
	
	public static final Logger logger = LoggerFactory.getLogger(TableModelExporter.class);
	
	public static final int ODS_FORMAT = 0;
	public static final int XLS_FORMAT = 1;
	
	private int format;
	
	public TableModelExporter(int format) throws IllegalArgumentException {
		checkFormat(format);
		this.format = format;
	}

	public File exportToTempFile(TableModel model, String prefix) throws IOException, WriteException {
		File out = null;
		try {
			out = File.createTempFile(prefix, getSuffix() );
			logger.debug("Creando fichero temporal '{}'",out.getAbsolutePath());
			out.deleteOnExit();
		} catch (IOException ex) {
			logger.error("Error creando fichero temporal");
			throw ex;
		}
		return exportToFile(out, model);
	}
	
	public File exportToFile(File file, TableModel model) throws FileNotFoundException, IOException, WriteException {
		
		if (format==ODS_FORMAT) 
			return SpreadSheet.createEmpty(model).saveAs(file);
		if (format==XLS_FORMAT) 
			return ExcelExporter.saveTableModelToFile(model, file);
		return file;
	}
	
	
	private void checkFormat(int format) throws IllegalArgumentException {
		if (format!=ODS_FORMAT && format!=XLS_FORMAT) {
			IllegalArgumentException e = 
					new IllegalArgumentException("Formato de exportacion incorrecto: "+format);
			e.fillInStackTrace();
			throw e;
		}
	}
	
	private String getSuffix() {
		if (format==ODS_FORMAT) return ".ods";
		if (format==XLS_FORMAT) return ".xls";
		return "";
	}
}
