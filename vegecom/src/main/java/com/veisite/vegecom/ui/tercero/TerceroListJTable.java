package com.veisite.vegecom.ui.tercero;

import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.veisite.vegecom.data.TerceroListProvider;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.components.table.AbstractListJTable;
import com.veisite.vegecom.ui.components.table.AbstractListTableModel;
import com.veisite.vegecom.ui.service.TerceroUIService;

public class TerceroListJTable<T extends TerceroComercial> extends AbstractListJTable<T> {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -5157503773928583494L;
	
	
	public TerceroListJTable(TerceroUIService<T> uiService) {
		super(new TerceroListTableModel<T>(uiService));
	}

	public TerceroListJTable(List<T> dataList, TerceroUIService<T> uiService) {
		super(new TerceroListTableModel<T>(dataList, uiService));
	}

	public TerceroListJTable(AbstractListTableModel<T> model) {
		super(model);
	}

	public TerceroListJTable(TerceroListProvider<T> dataProvider, TerceroUIService<T> uiService) {
		super(new TerceroListTableModel<T>(dataProvider, uiService));
	}

	@Override
	public void configureColumns() {
		TableColumnModel columnModel = getColumnModel();
		TableColumn col;
		col = columnModel.getColumn(TerceroListTableModel.CIF_COLUMN);
		configureCol(col,80,85,null);
		col = columnModel.getColumn(TerceroListTableModel.NOMBRE_COLUMN);
		configureCol(col,150,250,null);
		col = columnModel.getColumn(TerceroListTableModel.TELEFONO_COLUMN);
		configureCol(col,50,120,null);
		col = columnModel.getColumn(TerceroListTableModel.EMAIL_COLUMN);
		configureCol(col,100,120,null);
		col = columnModel.getColumn(TerceroListTableModel.PROVINCIA_COLUMN);
		configureCol(col,80,100,null);
		col = columnModel.getColumn(TerceroListTableModel.CPOSTAL_COLUMN);
		configureCol(col,40,40,null);
		col = columnModel.getColumn(TerceroListTableModel.MUNICIPIO_COLUMN);
		configureCol(col,80,120,null);
		col = columnModel.getColumn(TerceroListTableModel.DOMICILIO_COLUMN);
		configureCol(col,80,150,null);
	}

}
