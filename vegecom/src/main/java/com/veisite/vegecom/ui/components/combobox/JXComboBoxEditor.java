package com.veisite.vegecom.ui.components.combobox;

import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import org.jdesktop.swingx.JXTextField;

public class JXComboBoxEditor extends BasicComboBoxEditor {
	
	/**
	 * prompt Text
	 * @param promptText
	 */
	private String promptText = "";

	public JXComboBoxEditor() {
		super();
	}

	public JXComboBoxEditor(String promptText) {
		super();
		this.promptText = promptText;
	}

	/**
	 * @see javax.swing.plaf.basic.BasicComboBoxEditor#createEditorComponent()
	 */
	@Override
	protected JTextField createEditorComponent() {
		editor = new JXTextField(promptText);
		return editor;
	}

	/**
	 * @see javax.swing.plaf.basic.BasicComboBoxEditor#setItem(java.lang.Object)
	 */
	@Override
	public void setItem(Object anObject) {
		return;
	}
	
}
