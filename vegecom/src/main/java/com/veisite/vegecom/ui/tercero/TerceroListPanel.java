package com.veisite.vegecom.ui.tercero;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.data.TerceroListProvider;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.service.DataChangeListener;
import com.veisite.vegecom.ui.DeskApp;
import com.veisite.vegecom.ui.components.table.AbstractListJTable;
import com.veisite.vegecom.ui.components.table.AbstractListTablePanel;
import com.veisite.vegecom.ui.framework.views.UIFrameworkView;

public abstract class TerceroListPanel<T extends TerceroComercial> extends UIFrameworkView 
						implements DataChangeListener<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Panel para el filtrado de beneficiarios en la tabla.
	 */
	protected TerceroFilterTablePanel<T> filterPanel;
	/**
	 * Panel de la tabla de terceros
	 */
	protected AbstractListTablePanel<T> tablePanel;
	
	/**
	 * Fuente de datos de clientes
	 */
	protected TerceroListProvider<T> dataProvider;
	
	/**
	 * Opciones de menu contextual
	 */
	protected JMenuItem newTerceroMenu;
	protected JMenuItem editTerceroMenu;
	protected JMenuItem deleteTerceroMenu;
	
	
	public TerceroListPanel(String id, TerceroListProvider<T> dataProvider) 
			throws VegecomException {
		super(id);
		this.dataProvider = dataProvider;
		initComponent();
	}
	
	
	/**
	 * Creamos los componentes del Panel
	 * @throws GaslabException 
	 */
	protected void initComponent() throws VegecomException {
		setLayout(new BorderLayout());
		dataProvider.setBlockSize(50);
		TerceroListJTable<T> table =
				new TerceroListJTable<T>(dataProvider);
		tablePanel = new AbstractListTablePanel<T>(this,table) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void doubleClickOnTable() {
				doubleClickOnTercero();
			}
			@Override
			protected void enableDisablePopupMenu() {
				super.enableDisablePopupMenu();
				enablePopupMenu();
			}
		};
		configurePopupMenu();
		add(tablePanel,BorderLayout.CENTER);
		configureFilter();
		initSortOrder();
		// Quedamos a la escucha de cambios en el servicio de clientes para
		// reflajar los cambios que haya
		dataProvider.getDataService().addDataChangeListener(this);
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
	 * Configura el menu contextual para la edicion, alta y 
	 * borrado de terceros.
	 */
	protected void configurePopupMenu() {
		JPopupMenu pm = tablePanel.getPopupMenu();
		
		newTerceroMenu = new JMenuItem(new NewTerceroAction());
		editTerceroMenu = new JMenuItem(new EditTerceroAction());
		deleteTerceroMenu = new JMenuItem(new DeleteTerceroAction());
		
		pm.add(deleteTerceroMenu,0);
		pm.add(editTerceroMenu,0);
		pm.add(newTerceroMenu,0);
	}

	/**
	 * Activa/desactiva opciones de menu
	 */
	protected void enablePopupMenu() {
		AbstractListJTable<T> table = tablePanel.getTable();
		editTerceroMenu.setEnabled(table.getSelectedRowCount()==1);
		deleteTerceroMenu.setEnabled(table.getSelectedRowCount()==1);
	}

	/**
	 * Se ejecuta al hacer doble click en un tercero
	 * Por defecto se intenta editar la fila.
	 * Se puede sobreescribir el metodo para conseguir otro
	 * comportamiento, por ejemplo, seleccionar un tercero y 
	 * salir.
	 */
	protected void doubleClickOnTercero() {
		if (editTerceroMenu!=null)
			editTerceroMenu.doClick();
	}
	
	/**
	 * Devuelve el tercero que está seleccionado en la tabla
	 * null si no hay seleccion
	 */
	public T getSelectedTercero() {
		int rowView = tablePanel.getTable().getSelectedRow();
		if (rowView<0) return null;
		int row = tablePanel.getTable().convertRowIndexToModel(rowView);
		return tablePanel.getTable().getItemAt(row);
	}
	
	/**
	 * Metodo a implementar para dar de alta un nuevo tercero
	 * Devuelve el tercero si se dio de alta correctamente y null
	 * si no se dió de alta
	 */
	protected abstract T doNewTercero(Component parent);
	
	/**
	 * Metodo a implementar para eliminar un tercero
	 * Devuelve true se se dió de baja correctamente y 
	 * false en otro caso
	 */
	protected abstract boolean doDelTercero(Component parent, T tercero);
	
	/**
	 * Metodo a implementar para editar un tercero
	 * Devuelve el Tercero si se modifico correctamente y 
	 * null en otro caso
	 */
	protected abstract T doEditTercero(Component parent, T tercero);
	
	
	/**
	 * Implementacion de acciones de menu
	 */
	/**
	 * Da de alta un nuevo tercero
	 * 
	 * @author josemaria
	 */
	private class NewTerceroAction extends AbstractAction {
		/**
		 * serial
		 */
		private static final long serialVersionUID = 3651293287585063125L;

		public NewTerceroAction() {
			super(DeskApp.getMessage("ui.components.menu.New", null, "New"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			doNewTercero(tablePanel);
		}
	}
	
	/**
	 * Editar un tercero
	 * 
	 * @author josemaria
	 */
	private class EditTerceroAction extends AbstractAction {
		/**
		 * serial
		 */
		private static final long serialVersionUID = -4731589874417212249L;

		public EditTerceroAction() {
			super(DeskApp.getMessage("ui.components.menu.Edit", null, "Edit"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AbstractListJTable<T> table = tablePanel.getTable();
			if (table.getSelectedRowCount()!=1) return;
			int row = table.convertRowIndexToModel(table.getSelectedRow());
			T tercero = table.getItemAt(row);
			if (tercero==null) return;
			doEditTercero(tablePanel, tercero);
		}
	}
	
	/**
	 * Eliminar uno o varios terceros
	 * 
	 * @author josemaria
	 */
	private class DeleteTerceroAction extends AbstractAction {
		/**
		 * serial
		 */
		private static final long serialVersionUID = 7372447598794037254L;

		public DeleteTerceroAction() {
			super(DeskApp.getMessage("ui.components.menu.Delete", null, "Delete"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AbstractListJTable<T> table = tablePanel.getTable();
			if (table.getSelectedRowCount()!=1) return;
			// Vamos borrando cada uno de los terceros seleecionados
			for (int rowView : table.getSelectedRows()) {
				int row = table.convertRowIndexToModel(rowView);
				T tercero = table.getItemAt(row);
				if (tercero!=null) doDelTercero(tablePanel, tercero);
			}
		}
	}

	
	/**
	 * Un nuevo tercero ha sido añadido
	 * Incluir en la lista
	 */
	@Override
	public void itemAdded(T item) {
		AbstractListJTable<T> table = tablePanel.getTable();
		table.addItem(item);
	}


	/**
	 * Un tercero ha sido modificado, notificar la tabla
	 */
	@Override
	public void itemChanged(T item) {
		AbstractListJTable<T> table = tablePanel.getTable();
		int index = getModelIndexForItem(table, item);
		if (index>=0) table.setItemAt(index, item);
	}


	/* (non-Javadoc)
	 * @see com.veisite.vegecom.service.DataChangeListener#itemRemoved(java.lang.Object)
	 */
	@Override
	public void itemRemoved(T item) {
		AbstractListJTable<T> table = tablePanel.getTable();
		int index = getModelIndexForItem(table, item);
		if (index>=0) table.delItemAt(index);
	}
	
	/**
	 * Busca un elemento en la table y devuelve el indice en el modelo
	 * Devuelve -1 si no se encuetra.
	 * @param table
	 * @param item
	 * @return
	 */
	protected int getModelIndexForItem(AbstractListJTable<T> table, T item) {
		if (table.getModel()!=null) {
			List<T> lista = table.getModel().getDataList();
			for (int i=0; i<lista.size(); i++)
				if (lista.get(i).getId().equals(item.getId()))
					return i;
		}
		return -1;
	}
	
	
}
