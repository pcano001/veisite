package com.veisite.vegecom.ui.tercero;

import javax.swing.JPanel;

import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.DeskApp;

public abstract class TerceroEditPanel extends JPanel {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -5863438742043219413L;

	/**
	 * tercero a editar
	 */
	private TerceroComercial tercero;
	
	/**
	 * Componentes
	 */
	
	
	
	
	/**
	 * Constructor
	 * @param tercero
	 */
	public TerceroEditPanel(TerceroComercial tercero) {
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
	public TerceroComercial getTercero() {
		return tercero;
	}
	
	private void createComponents() {
		
	}
	
	private void composePanel() {
		
	}
	
	private void bindComponents() {
		
	}

}
