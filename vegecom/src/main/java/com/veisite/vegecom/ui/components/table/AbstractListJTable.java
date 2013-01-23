package com.veisite.vegecom.ui.components.table;

import javax.swing.JTable;

import org.jdesktop.swingx.JXTable;

/**
 * Clase que imlpementa una tabla abstracta. que hace una
 * inicialización por defecto de aspectos de configuración
 * 
 *  Por defecto se define para un tipo de objeto que es el que
 *  contiene la información que se pasa al modelo.
 * 
 * @author josemaria
 *
 */
public abstract class AbstractListJTable<T> extends JXTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Modelo
	 */
	protected AbstractListTableModel<T> model;

	/**
	 * Constructor de tabla con el modelo.
	 */
	public AbstractListJTable(AbstractListTableModel<T> model) {
		super(model);
		this.model = model;
		init();
	}
	
	private void init(){
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnControlVisible(true);
		setBackground(new javax.swing.plaf.ColorUIResource(250,250,250));
		setAutoCreateRowSorter(true);
		if (model!=null)
			model.configureColumns(getColumnModel());
	}
	
	/**
	 * get the model
	 */
	@SuppressWarnings("unchecked")
	public AbstractListTableModel<T> getModel() {
		return ((AbstractListTableModel<T>) super.getModel());
	}
	
	/**
	 * @param model the model to set
	 */
	public void setModel(AbstractListTableModel<T> model) {
		super.setModel(model);
		this.model = model;
		model.configureColumns(getColumnModel());
	}

	/**
	 * Devuelve el objeto  de la fila indicada
	 * @param row
	 * @return
	 */
	public T getItemAt(int row) {
		return model.getItemAt(row);
	}

	/**
	 * Cambia un object por el indicado en la fila especificada
	 * @param row, beneficiario
	 * @return
	 */
	public void setItemAt(int row, T item) {
		model.setItemAt(row, item);
	}

}

