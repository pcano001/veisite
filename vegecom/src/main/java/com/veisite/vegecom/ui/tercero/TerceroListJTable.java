package com.veisite.vegecom.ui.tercero;

import java.util.List;

import com.veisite.vegecom.data.TerceroListProvider;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.components.table.AbstractListJTable;
import com.veisite.vegecom.ui.components.table.AbstractListTableModel;

public class TerceroListJTable<T extends TerceroComercial> extends AbstractListJTable<T> {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -5157503773928583494L;
	
	
	public TerceroListJTable() {
		super(new TerceroListTableModel<T>());
	}

	public TerceroListJTable(List<T> dataList) {
		super(new TerceroListTableModel<T>(dataList));
	}

	public TerceroListJTable(AbstractListTableModel<T> model) {
		super(model);
	}

	public TerceroListJTable(TerceroListProvider<T> dataProvider) {
		super(new TerceroListTableModel<T>(dataProvider));
	}

}