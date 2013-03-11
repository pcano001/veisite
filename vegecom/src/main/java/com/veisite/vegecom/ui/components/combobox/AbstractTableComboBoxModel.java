package com.veisite.vegecom.ui.components.combobox;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.RowFilter;

import org.springframework.util.Assert;

import com.veisite.vegecom.ui.components.table.AbstractListJTable;
import com.veisite.vegecom.ui.components.table.AbstractListTableModel;

public abstract class AbstractTableComboBoxModel<T> extends AbstractListModel<T> implements ComboBoxModel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7856216195305872947L;
	
	/**
	 * Table que sirve de soporte al modelo.
	 * 
	 * @param table
	 */
	private AbstractListJTable<T> table;
	
	
	public AbstractTableComboBoxModel(AbstractListJTable<T> table) {
		Assert.notNull(table, "table must be not null");
		this.table = table;
	}
	
	
	@Override
	public int getSize() {
		return table.getRowCount();
	}

	@Override
	public T getElementAt(int index) {
		int row = table.convertRowIndexToModel(index);
		return table.getModel().getItemAt(row);
	}

	/**
	 * @return the table
	 */
	public AbstractListJTable<T> getTable() {
		return table;
	}

	/**
	 * Establece filtro en la table
	 */
	public void applyTableFilter(RowFilter<AbstractListTableModel<T>, Integer> filter) {
		int end = getSize()-1;
		table.setRowFilter(filter);
		fireIntervalRemoved(this, 0, end);
		fireIntervalAdded(this, 0, getSize());
	}

}
