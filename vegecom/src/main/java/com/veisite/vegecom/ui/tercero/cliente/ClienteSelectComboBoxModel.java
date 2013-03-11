package com.veisite.vegecom.ui.tercero.cliente;

import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.ui.components.combobox.AbstractTableComboBoxModel;
import com.veisite.vegecom.ui.components.table.AbstractListJTable;

public class ClienteSelectComboBoxModel extends AbstractTableComboBoxModel<Cliente> {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 8831655888292538167L;
	
	/**
	 * Selected cliente on combo
	 */
	private Cliente selectedCliente = null;
	

	public ClienteSelectComboBoxModel(AbstractListJTable<Cliente> table) {
		super(table);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		if (anItem instanceof Cliente) {
			selectedCliente = (Cliente) anItem;
			
		}
	}

	@Override
	public Object getSelectedItem() {
		return selectedCliente;
	}

}
