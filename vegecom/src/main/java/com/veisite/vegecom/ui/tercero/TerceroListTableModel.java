package com.veisite.vegecom.ui.tercero;

import java.util.List;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veisite.vegecom.data.TerceroListProvider;
import com.veisite.vegecom.model.Municipio;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.components.table.AbstractListTableModel;

/**
 * Clase que implementa el modelo de tabla de un tercero. 
 * 
 * Un tercero puede ser un cliente o un proveedor y muestra los
 * datos generales en formato de tabla con las siguientes columnas:
 * 
 * 	0 - cif
 * 	1 - nombre
 * 	2 - telefono
 * 	3 - email
 * 	4 - provincia
 * 	5 - codigo postal
 * 	6 - localidad+municipio
 * 	7 - domicilio
 * 	
 * 
 * @author josemaria
 *
 */
public class TerceroListTableModel<T extends TerceroComercial> 
									extends AbstractListTableModel<T> {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 4478333403718532551L;

	private static Logger logger = LoggerFactory.getLogger(TerceroListTableModel.class);

	public TerceroListTableModel(TerceroListProvider<T> dataProvider) {
		super(dataProvider);
		logger.debug("Creating a Tercero List Table Model with dataProvider");
	}
	
	public TerceroListTableModel(List<T> dataList) {
		super(dataList);
		logger.debug("Creating a Tercero List Table Model with dataList");
	}
	
	public TerceroListTableModel() {
		super();
		logger.debug("Creating a Tercero List Table Model with empty data");
	}
	
	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TerceroComercial item = getItemAt(rowIndex);
		if (item==null) return null;
		switch (columnIndex) {
		case 0:
			return item.getCif();
		case 1:
			return item.getNombre();
		case 2:
			return item.getTelefono();
		case 3:
			return item.getEmail();
		case 4:
			return item.getProvincia()==null ? null : item.getProvincia().getNombre();
		case 5:
			return item.getCodigoPostal();
		case 6:
			String l = "";
			if (item.getLocalidad()!=null) l = item.getLocalidad().trim();
			Municipio m = item.getMunicipio();
			if (m!=null) {
				if (!l.isEmpty()) l+=" ("+m.getNombre()+")";
				else l += m.getNombre();
			}
			return l;
		case 7:
			return item.getDomicilio();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Cif";
		case 1:
			return "Nombre";
		case 2:
			return "Teléfono";
		case 3:
			return "e-mail";
		case 4:
			return "Provincia";
		case 5:
			return "C.Postal";
		case 6:
			return "Municipio";
		case 7:
			return "Domicilio";
		}
		return super.getColumnName(column);
	}

	
	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex<getColumnCount()) return String.class;
		return super.getColumnClass(columnIndex);
	}

	@Override
	public void configureColumns(TableColumnModel columnModel) {
		int i=0;
		TableColumn col;
		col = columnModel.getColumn(i++);
		configureCol(col,80,85,null);
		col = columnModel.getColumn(i++);
		configureCol(col,150,250,null);
		col = columnModel.getColumn(i++);
		configureCol(col,50,120,null);
		col = columnModel.getColumn(i++);
		configureCol(col,100,120,null);
		col = columnModel.getColumn(i++);
		configureCol(col,80,100,null);
		col = columnModel.getColumn(i++);
		configureCol(col,40,40,null);
		col = columnModel.getColumn(i++);
		configureCol(col,80,120,null);
		col = columnModel.getColumn(i++);
		configureCol(col,80,150,null);
	}

	private void configureCol(TableColumn col, int minWidth, int preferredWidth, TableCellRenderer renderer) {
		col.setMinWidth(minWidth);
		col.setPreferredWidth(preferredWidth);
		if (renderer!=null) col.setCellRenderer(renderer);
	}
	
}
