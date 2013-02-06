package com.veisite.vegecom.ui.tercero;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.veisite.vegecom.binding.BindTarget;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.DeskApp;
import com.veisite.vegecom.ui.components.CifTextField;
import com.veisite.vegecom.ui.components.VTextField;
import com.veisite.vegecom.ui.util.UIResources;

public abstract class TerceroEditComponent<T extends TerceroComercial> extends JPanel {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -5863438742043219413L;

	/**
	 * tercero a editar
	 */
	private T tercero;
	
	/**
	 * Componentes
	 */
	protected CifTextField cifField;
	protected VTextField nameField;
	
	
	/**
	 * Constructor
	 * @param tercero
	 */
	public TerceroEditComponent(T tercero) {
		if (tercero==null) {
			String m = 
				DeskApp.getMessage("com.veisite.vegecom.ui.tercero.TerceroEditPanel.nullTercero", 
						null, "paramter 'tercero' cannot be null.");
			throw new IllegalArgumentException(m);
		}
		this.tercero = tercero;
		createComponents();
		composePanel();
		bindComponents();
	}

	/**
	 * @return the tercero
	 */
	public T getTercero() {
		return tercero;
	}
	
	protected void createComponents() {
		String s;
		s =	DeskApp.getMessage("ui.tercero.TerceroEditPanel.cifPrompt", null, "Cif");
		cifField = new CifTextField(s, 8);
		s =	DeskApp.getMessage("ui.tercero.TerceroEditPanel.namePrompt", null, "Name");
		nameField = new VTextField(s);
		nameField.setColumns(20);
	}
	
	protected void composePanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel vBox = new JPanel();
	    vBox.setLayout(new BoxLayout(vBox,BoxLayout.Y_AXIS));
	    
	    JPanel row;
		String s;
	    
	    row = new JPanel(new FlowLayout(FlowLayout.LEFT));
		s =	DeskApp.getMessage("ui.tercero.TerceroEditPanel.cifLabel", null, "Cif:");
	    row.add(UIResources.getLabeledComponent(s, cifField));
		s =	DeskApp.getMessage("ui.tercero.TerceroEditPanel.nameLabel", null, "Name:");
	    row.add(UIResources.getLabeledComponent(s, nameField));
	    vBox.add(row);
	    
	    add(vBox);
	}
	
	protected void bindComponents() {
		cifField.setText(tercero.getCif());
		cifField.addBindTo(new BindTarget<String>(tercero, "cif"));
		nameField.setText(tercero.getNombre());
		nameField.addBindTo(new BindTarget<String>(tercero, "nombre"));
	}

}
