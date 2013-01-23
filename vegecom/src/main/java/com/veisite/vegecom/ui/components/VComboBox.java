package com.veisite.vegecom.ui.components;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import com.veisite.vegecom.binding.BindTarget;
import com.veisite.vegecom.binding.IBindableTo;

public class VComboBox extends JComboBox implements IActivableComponent, 
													IBindableTo<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * binding selectedItem to an Object property on this target
	 */
	List<BindTarget<Object>> bindList = new ArrayList<BindTarget<Object>>();
	
	
	public VComboBox() {
		initComponent();
	}

	public VComboBox(ComboBoxModel aModel) {
		super(aModel);
		initComponent();
	}

	public VComboBox(Object[] items) {
		super(items);
		initComponent();
	}

	public VComboBox(Vector<?> items) {
		super(items);
		initComponent();
	}

	private void initComponent() {
		addItemListener(new ItemChangeListener());
	}
	
	/**
	 * Simulate a property to enable/disable component
	 */
	@Override
	public Boolean getActivado() {
		return new Boolean(super.isEnabled());
	}

	@Override
	public void setActivado(Boolean newActivado) {
		Boolean oldValue = getActivado();
		boolean b = (newActivado!=null && newActivado.booleanValue());
		super.setEnabled(b);
		firePropertyChange("activado", oldValue, getActivado());
	}

	@Override
	public void addBindTo(BindTarget<Object> target) {
		if (target==null) return;
		bindList.add(target);
	}

	@Override
	public void removeBindTo(BindTarget<Object> target) {
		if (target==null) return;
		bindList.remove(target);
	}

	
	private void itemChanged() {
		for (BindTarget<Object> b : bindList) 
			b.setValue(getSelectedItem());
	}
	
	
	/**
	 * Manage changes on selectedItem
	 * @author josemaria
	 *
	 */
	private class ItemChangeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			itemChanged();
		}
	}

}
