package com.veisite.vegecom.ui.tercero;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.event.EventListenerList;

import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.components.VTextField;

public class TerceroFilterTablePanel<T extends TerceroComercial> extends JPanel {
	
	/**
	 * serial
	 */
	private static final long serialVersionUID = 2764915812999391758L;

	/**
	 * Listener para eventos de cambio
	 */
    private EventListenerList listenerList = new EventListenerList();
    
    private VTextField searchField;
	
	public TerceroFilterTablePanel() {
		super(new BorderLayout());
		setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));
		configurePanel();
	}

	private void configurePanel() {
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	 	searchField = new VTextField("Buscar/Filtro:");
	 	searchField.setColumns(20);
	 	//TODO [quitar]
	 	//filterPanel.add(UIResources.getEditComponent("Buscar/Filtro", searchField, false, true));
	 	
	 	add(filterPanel, BorderLayout.CENTER);
	 	
	 	/* Bottom panel */
	 	JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	 	JButton applyButton =  new JButton("Aplicar filtro");
	 	actionPanel.add(applyButton);
	 	add(actionPanel, BorderLayout.SOUTH);
	 	
	 	ActionListener changeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireFilterChanged();
			}
	 	};
	 	searchField.addActionListener(changeListener);
	 	applyButton.addActionListener(changeListener);
	}

	
	public RowFilter<TerceroListTableModel<T>, Integer> getRowFilter() {
		List<RowFilter<TerceroListTableModel<T>, Integer>> filters = 
				new ArrayList<RowFilter<TerceroListTableModel<T>, Integer>>();

		String search = searchField.getText().trim();
		if (!search.isEmpty()) {
			RowFilter<TerceroListTableModel<T>, Integer> searchFilter = 
					RowFilter.regexFilter(search); 
			filters.add(searchFilter);
		}
		if (filters.isEmpty()) return null;
		RowFilter<TerceroListTableModel<T>, Integer> allFilter = RowFilter.andFilter(filters);		
		return allFilter;
	}
	
	/**
	 * Gesti�n de eventos en la lista de datos A�ade un data listener a la lista
	 * de datos
	 */
	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	/**
	 * Elimina un data listener de la lista de datos
	 */
	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	/* Notify all listener */
	/* Selection changed */
	protected void fireFilterChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListeners(ActionListener.class);
		ActionEvent e = new ActionEvent(this,0,"changed");

		for (int i = listeners.length - 1; i >= 0; i--)
			((ActionListener) listeners[i]).actionPerformed(e);
	}
	
}
