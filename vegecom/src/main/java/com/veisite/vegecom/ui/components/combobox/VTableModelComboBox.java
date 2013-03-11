package com.veisite.vegecom.ui.components.combobox;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;

import org.jdesktop.swingx.JXTextField;

import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.ui.components.VComboBox;
import com.veisite.vegecom.ui.components.table.AbstractListTableModel;
import com.veisite.vegecom.ui.components.table.filter.NoAcentosRegexFilter;
import com.veisite.vegecom.util.StringUtil;

public class VTableModelComboBox<T> extends VComboBox<T> 
			implements DocumentListener, FocusListener {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 7871657189728833085L;
	
	/**
	 * editor del combo
	 * 
	 */
	private JXTextField editor; 
	

	public VTableModelComboBox() {
		this("");
	}

	public VTableModelComboBox(String promptText) {
		super();
		initComponent(promptText);
	}

	public VTableModelComboBox(AbstractTableComboBoxModel<T> model, String promptText) {
		super(model);
		initComponent(promptText);
	}
	
	private void initComponent(String promptText) {
		// Poner editor para buscar.
		setUI(new BasicComboBoxUI() {
		    protected JButton createArrowButton() {
		        return new JButton() {
					private static final long serialVersionUID = 1L;
					public int getWidth() {
		                return 0;
		            }
		        };
		    }
		});
		setEditor(new JXComboBoxEditor(promptText));
		editor = (JXTextField) getEditor().getEditorComponent();
		editor.getDocument().addDocumentListener(this);
		editor.addFocusListener(this);
		setEditable(true);
	}


	/**
	 * El contenido del editor ha cambiado, hay que filtrar la tabla
	 */
	private void filterChanged() {
		hidePopup();
		AbstractTableComboBoxModel<T> model = (AbstractTableComboBoxModel<T>) getModel();
		String text = editor.getText().trim();
		if (text.isEmpty()) return;
		RowFilter<AbstractListTableModel<T>, Integer> asciiFilter =
				NoAcentosRegexFilter.createNoAcentosFilter(true, 
						StringUtil.quitaAcentos(text.toUpperCase()));
		model.applyTableFilter(asciiFilter);
		invalidate();
		repaint();
		showPopup();
	}
	
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		filterChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		filterChanged();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		filterChanged();
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		hidePopup();
		Cliente c = (Cliente) getSelectedItem();
	}
	
	/**
	 * Devuelve el texto de prompt
	 * @return
	 */
	public String getPromptText() {
		if (editor!=null) editor.getPrompt();
		return "";
	}
	
	/**
	 * Establece el texto de prompt
	 * 
	 * @param promptText
	 */
	public void setPromptText(String promptText) {
		if (editor!=null) editor.setPrompt(promptText);
	}

}
