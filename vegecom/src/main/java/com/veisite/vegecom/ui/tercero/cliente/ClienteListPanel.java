package com.veisite.vegecom.ui.tercero.cliente;

import java.awt.Component;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.data.ClienteListProvider;
import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.ui.tercero.TerceroListPanel;

public class ClienteListPanel extends TerceroListPanel<Cliente> {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -4084507772883195291L;
	
	/**
	 * Servicio grafico de clientes
	 */
	private ClienteUIService uiService;
	
	
	public ClienteListPanel(ClienteUIService uiService, ClienteListProvider dataProvider) throws VegecomException {
		super("clienteListView", dataProvider, uiService.getUiInstance());
		this.uiService = uiService;
		setTitle(uiService.getUiInstance().getMessage("ui.ClientesModule.ClientesViewTitle", null, "Customers"));
	}

	@Override
	protected Cliente doNewTercero(Component parent) {
		return uiService.newCliente(parent);
	}
	
	@Override
	protected Cliente doEditTercero(Component parent, Cliente cliente) {
		return uiService.editCliente(cliente, parent);
	}

	@Override
	protected boolean doDelTercero(Component parent, Cliente cliente) {
		return (uiService.removeCliente(cliente, true, parent)!=null);
	}

}
