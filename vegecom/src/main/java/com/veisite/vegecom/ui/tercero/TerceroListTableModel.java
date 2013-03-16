package com.veisite.vegecom.ui.tercero;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.veisite.vegecom.data.TerceroListProvider;
import com.veisite.vegecom.model.Municipio;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.components.table.AbstractListTableModel;
import com.veisite.vegecom.ui.service.TerceroUIService;

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
	
	// Definicion de columnas del modelo
	public static final int CIF_COLUMN = 		00;
	public static final int NOMBRE_COLUMN =		01;
	public static final int TELEFONO_COLUMN =	02;
	public static final int EMAIL_COLUMN = 		03;
	public static final int PROVINCIA_COLUMN = 	04;
	public static final int CPOSTAL_COLUMN = 	05;
	public static final int MUNICIPIO_COLUMN = 	06;
	public static final int DOMICILIO_COLUMN = 	07;
	
	
	/**
	 * recursos graficos para cadenas de texto
	 * 
	 */
	private TerceroUIService<T> uiService;

	public TerceroListTableModel(TerceroListProvider<T> dataProvider, TerceroUIService<T> uiService) {
		super(dataProvider);
		Assert.notNull(uiService);
		this.uiService = uiService;
		logger.debug("Creating a Tercero List Table Model with dataProvider");
	}
	
	public TerceroListTableModel(List<T> dataList, TerceroUIService<T> uiService) {
		super(dataList);
		Assert.notNull(uiService);
		this.uiService = uiService;
		logger.debug("Creating a Tercero List Table Model with dataList");
	}
	
	public TerceroListTableModel(TerceroUIService<T> uiService) {
		super();
		Assert.notNull(uiService);
		this.uiService = uiService;
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
		case CIF_COLUMN:
			return item.getNif();
		case NOMBRE_COLUMN:
			return item.getNombre();
		case TELEFONO_COLUMN:
			return item.getTelefono();
		case EMAIL_COLUMN:
			return item.getEmail();
		case PROVINCIA_COLUMN:
			return item.getProvincia()==null ? null : item.getProvincia().getNombre();
		case CPOSTAL_COLUMN:
			return item.getCodigoPostal();
		case MUNICIPIO_COLUMN:
			String l = "";
			if (item.getLocalidad()!=null) l = item.getLocalidad().trim();
			Municipio m = item.getMunicipio();
			if (m!=null) {
				if (!l.isEmpty()) l+=" ("+m.getNombre()+")";
				else l += m.getNombre();
			}
			return l;
		case DOMICILIO_COLUMN:
			return item.getDomicilio();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case CIF_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.cifLabel", null, "Nif");
		case NOMBRE_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.nameLabel", null, "Nif");
		case TELEFONO_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.telefonoLabel", null, "Nif");
		case EMAIL_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.emailLabel", null, "Nif");
		case PROVINCIA_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.provinciaLabel", null, "Nif");
		case CPOSTAL_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.cpostalLabel", null, "Nif");
		case MUNICIPIO_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.municipioLabel", null, "Nif");
		case DOMICILIO_COLUMN:
			return uiService.getMessage("ui.tercero.TerceroListTable.domicilioLabel", null, "Nif");
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

}
