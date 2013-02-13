package com.veisite.vegecom.ui.tercero.cliente;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.ui.DeskApp;
import com.veisite.vegecom.ui.components.dialogs.AbstractEditDialog;
import com.veisite.vegecom.ui.components.panels.ValidationMessagesPanel;

public class ClienteEditDialog extends AbstractEditDialog {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -7349392482605593043L;

	/**
	 * El cliente que se va a editar
	 */
	private Cliente cliente;
	
	
	public ClienteEditDialog(Dialog owner, Cliente cliente) {
		super(owner);
		this.cliente = cliente;
		initializeDialog();
	}

	public ClienteEditDialog(Frame owner, Cliente cliente) {
		super(owner);
		this.cliente = cliente;
		initializeDialog();
	}

	public ClienteEditDialog(Window owner, Cliente cliente) {
		super(owner);
		this.cliente = cliente;
		initializeDialog();
	}

	public ClienteEditDialog(Component parent, Cliente cliente) {
		super(parent);
		this.cliente = cliente;
		initializeDialog();
	}
	
	private void initializeDialog() {
		setTitle(cliente.getNombre());
		// Poner escucha a los cambios en el cliente
		cliente.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				setContentDirty(true);
			}
		});
		// Poner el componente de edición de cliente.
		ClienteEditComponent c = new ClienteEditComponent(cliente);
		setContentComponent(c);
	}

	@Override
	protected boolean canClose() {
		// Hacemos las validaciones para poder cerrar el dialogo.
		Validator validator = DeskApp.getValidator();
		if (validator!=null) {
			Set<ConstraintViolation<Cliente>> constraintViolations =
					validator.validate(cliente);
			if (constraintViolations.size()>0) {
				String s = 
						DeskApp.getMessage("ui.tercero.cliente.ClienteEditDialog.ValidationErrorMessage", 
						null, "Customer data has errors");
				ValidationMessagesPanel<Cliente> p = 
						new ValidationMessagesPanel<Cliente>(s,constraintViolations);
				JOptionPane.showMessageDialog(this, p, s, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean canCancel() {
		return true;
	}

	@Override
	protected void onClose() {
	}
	
}
