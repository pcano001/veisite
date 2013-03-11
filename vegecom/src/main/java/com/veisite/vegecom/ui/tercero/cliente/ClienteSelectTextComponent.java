package com.veisite.vegecom.ui.tercero.cliente;

import java.awt.Dimension;

import org.springframework.util.Assert;

import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.ui.components.combobox.VTableModelComboBox;
import com.veisite.vegecom.ui.service.ClienteUIService;
import com.veisite.vegecom.ui.tercero.TerceroListJTable;

public class ClienteSelectTextComponent extends VTableModelComboBox<Cliente> {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 8731141729029759193L;
	
	public ClienteSelectTextComponent(String promptText, ClienteUIService uiService) {
		super(promptText);
		Assert.notNull(uiService);
		initComponent(uiService);
	}
	
	private void initComponent(ClienteUIService uiService) {
		// Establecer el modelo.
		TerceroListJTable<Cliente> table = new TerceroListJTable<Cliente>(uiService.getListTableModel());
		setModel(new ClienteSelectComboBoxModel(table));
		setPreferredSize(new Dimension(150,getPreferredSize().height));
	}

}
