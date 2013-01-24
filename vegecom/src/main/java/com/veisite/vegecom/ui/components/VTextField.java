package com.veisite.vegecom.ui.components;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;

import com.veisite.vegecom.binding.BindTarget;
import com.veisite.vegecom.binding.IBindableTo;

public class VTextField extends JXTextField implements IActivableComponent, 
														 IBindableTo<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * binding text to a String property on this target
	 */
	List<BindTarget<String>> bindList = new ArrayList<BindTarget<String>>();
	
	
	public VTextField() {
		super();
		initComponent();
	}

	public VTextField(String promptText, Color promptForeground,
			Color promptBackground) {
		super(promptText, promptForeground, promptBackground);
		initComponent();
	}

	public VTextField(String promptText, Color promptForeground) {
		super(promptText, promptForeground);
		initComponent();
	}

	public VTextField(String promptText) {
		super(promptText);
		initComponent();
	}


	private void initComponent() {
		getDocument().addDocumentListener(new BindingDocumentListener());
		addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                int key = evt.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    transferFocus();
                    evt.consume();
                }
            }
        });
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
	public void addBindTo(BindTarget<String> target) {
		if (target==null) return;
		bindList.add(target);
	}

	public void removeBindTo(BindTarget<String> target) {
		if (target==null) return;
		bindList.remove(target);
	}

	/**
	 * Ha cambiado el texto. 
	 * Sincronizar los binding
	 */
	private void documentChanged() {
		for (BindTarget<String> b : bindList) 
			b.setValue(getText());
	}
	
	private class BindingDocumentListener implements DocumentListener {
		@Override
		public void removeUpdate(DocumentEvent arg0) {
			documentChanged();
		}
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			documentChanged();
		}
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			documentChanged();
		}
	}

}
