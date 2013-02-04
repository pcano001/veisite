package com.veisite.vegecom.ui.tercero;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.data.TerceroListProvider;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.ui.components.table.AbstractListJTable;
import com.veisite.vegecom.ui.components.table.AbstractListTablePanel;

public abstract class TerceroListPanel<T extends TerceroComercial> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Panel para el filtrado de beneficiarios en la tabla.
	 */
	protected TerceroFilterTablePanel<T> filterPanel;
	
	protected AbstractListTablePanel<T> tablePanel;
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(TerceroListPanel.class);
	
	public TerceroListPanel() throws VegecomException {
		super();
		initComponent();
		logger.debug("Creating a Tercero List Panel...");
	}
	
	
	/**
	 * Creamos los componentes del Panel
	 * @throws GaslabException 
	 */
	private void initComponent() throws VegecomException {
		setLayout(new BorderLayout());
		TerceroListProvider<T> dataProvider = getDataProvider();
		dataProvider.setBlockSize(50);
		TerceroListJTable<T> table =
				new TerceroListJTable<T>(dataProvider);
		tablePanel = new AbstractListTablePanel<T>(this,table) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doubleClickOnTable() {
				editTercero();
			}
		};
		configurePopupMenu();
		add(tablePanel,BorderLayout.CENTER);
		configureFilter();
		initSortOrder();
	}

	/**
	 * Configura y pone visible el panel de filtro de beneficiarios
	 */
	private void configureFilter() {
		filterPanel = new TerceroFilterTablePanel<T>();
		add(filterPanel,BorderLayout.NORTH);
		filterPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applyTableFilter();
			}
		});
	}
	
	private void applyTableFilter() {
		AbstractListJTable<T> table = tablePanel.getTable();
		table.setRowFilter(filterPanel.getRowFilter());
		tablePanel.updateStatusBar();
	}
	
	private void initSortOrder() {
		AbstractListJTable<T> table = tablePanel.getTable();
		SortKey sk = new SortKey(1, SortOrder.ASCENDING);
		List<SortKey> keys = new ArrayList<SortKey>(); 
		keys.add(sk);
		table.getRowSorter().setSortKeys(keys);
	}
	
	/**
	 * Configura el menu contextual
	 */
	protected abstract void configurePopupMenu();
	
	/**
	 * Edita un tercero al hacer doble click
	 */
	protected abstract void editTercero();
	
	/**
	 * Devuelve la proveedor de datos de la ista de terceros
	 */
	protected abstract TerceroListProvider<T> getDataProvider();
	
}
