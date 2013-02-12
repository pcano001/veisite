package com.veisite.vegecom.ui.tercero.cliente;

import java.awt.Component;
import java.awt.Dialog.ModalityType;

import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.data.ClienteListProvider;
import com.veisite.vegecom.data.TerceroListProvider;
import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.service.ClienteService;
import com.veisite.vegecom.ui.DeskApp;
import com.veisite.vegecom.ui.tercero.TerceroListPanel;

public class ClienteListPanel extends TerceroListPanel<Cliente> {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -4084507772883195291L;
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ClienteListPanel.class);

	/**
	 * Proveedor de lista de clientes 
	 */
	private ClienteListProvider dataProvider;
	
	/**
	 * Servicio de persistencia de clientes
	 */
	private ClienteService dataService;
	
	public ClienteListPanel() throws VegecomException {
		super();
		dataService = DeskApp.getContext().getBean(ClienteService.class);
	}

	@Override
	protected TerceroListProvider<Cliente> getDataProvider() throws VegecomException {
		if (dataProvider==null)
			dataProvider=new ClienteListProvider();
		return dataProvider;
	}

	@Override
	protected Cliente doNewTercero(Component parent) {
		Cliente cliente = new Cliente();
		String s = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.DefaultName", 
				null, "New cliente");
		cliente.setNombre(s);
		return doEditTercero(parent, cliente);
	}
	
	@Override
	protected Cliente doEditTercero(Component parent, Cliente cliente) {
		boolean error=false;
		Throwable excep=null;
		if (cliente.getId()!=null) {
			try {
				cliente = dataService.getById(cliente.getId());
			} catch (DataAccessException e) {
				excep = e;
				error = true;
			} catch (Throwable t) {
				excep = t;
				error = true;
			}
			if (error) {
				logger.error("Error al recueprar cliente en el servicio de persistencia", excep);
				String t = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorLoadClienteTitle", 
						null, "Error retrieving customer");
				String m = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorLoadClienteMessage", 
						null, "Error retrieving customer data");
				ErrorInfo err = new ErrorInfo(t, m,	excep.getMessage(), null, excep, null, null);
				JXErrorPane.showDialog(parent, err);
				return null;
			}
			// Si el cliente es null, no se ha encontrado
			// Podría haber sido borrado
			if (cliente==null) {
				logger.error("Error al recuperar cliente en el servicio de persistencia. No se encuentra.");
				String t = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorNotExistClienteTitle", 
						null, "Error retrieving customer");
				String m = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorNotExistClienteMessage", 
						null, "Customer not found. Please, refresh data");
				ErrorInfo err = new ErrorInfo(t, m,	m, null, null, null, null);
				JXErrorPane.showDialog(parent, err);
				return null;
			}
		}
		ClienteEditDialog dialog = new ClienteEditDialog(this, cliente);
		dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		dialog.pack();
		dialog.setLocationRelativeTo(dialog.getOwner());
		dialog.setVisible(true);
		if (dialog.getExitCode()!=JOptionPane.OK_OPTION || !dialog.isContentDirty()) return null;
		try {
			cliente = dataService.save(cliente);
		} catch (DataAccessException e) {
			excep = e;
			error = true;
		} catch (Throwable t) {
			excep = t;
			error = true;
		}
		if (error) {
			logger.error("Error al grabar el cliente en el servicio de persistencia", excep);
			String t = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorSaveClienteTitle", 
					null, "Error retrieving customer");
			String m = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorSaveClienteMessage", 
					null, "Error retrieving customer data");
			ErrorInfo err = new ErrorInfo(t, m,	excep.getMessage(), null, excep, null, null);
			JXErrorPane.showDialog(parent, err);
			return null;
		}
		return cliente;
	}

	@Override
	protected boolean doDelTercero(Component parent, Cliente cliente) {
		boolean error=false;
		Throwable excep=null;
		try {
			dataService.remove(cliente);
		} catch (DataAccessException e) {
			excep = e;
			error = true;
		} catch (Throwable t) {
			excep = t;
			error = true;
		}
		if (error) {
			logger.error("Error al eliminar el cliente en el servicio de persistencia", excep);
			String t = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorDeleteClienteTitle", 
					null, "Error deleting customer");
			String m = DeskApp.getMessage("ui.tercero.cliente.ClienteListPanel.ErrorDeleteClienteMessage", 
					null, "Error trying to remove customer data");
			ErrorInfo err = new ErrorInfo(t, m,	excep.getMessage(), null, excep, null, null);
			JXErrorPane.showDialog(parent, err);
			return false;
		}
		return true;
	}

}
