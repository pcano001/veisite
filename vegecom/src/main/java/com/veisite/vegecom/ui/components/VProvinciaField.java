package com.veisite.vegecom.ui.components;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.veisite.vegecom.model.Provincia;
import com.veisite.vegecom.service.AddressService;
import com.veisite.vegecom.ui.DeskApp;

public class VProvinciaField extends VComboBox<Provincia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Servicio para recuperación de provincias.
	 */
	private AddressService as;
	
	/**
	 * Lista de provincias
	 */
	List<com.veisite.vegecom.model.Provincia> lista;
	
	public VProvinciaField() {
		super();
		as = (AddressService) DeskApp.getContext().getBean(AddressService.class);
		this.lista = as.getProvincias();
		Vector<Provincia> v = new Vector<Provincia>(this.lista);
		// Insert empty value ""
		v.add(0, null);
		ComboBoxModel<Provincia> model = new DefaultComboBoxModel<Provincia>(v);
		setModel(model);
		Provincia pro = new Provincia();
		pro.setNombre("GUADALAJARA  ");
		setPrototypeDisplayValue(pro);
	}

	public void setProvinciaFromCode(String code, boolean confirmar) {
		// Si el codigo es la provincia actual, no hay que hacer nada
		Provincia current = (Provincia) getSelectedItem();
		if (current!=null && current.getId().equals(code)) return;
		// Validar codigo
		if (!isValidCode(code)) return;
		// Buscar valor en lista
		Provincia prov = null;
		for (Provincia p : lista) {
			if (p.getId().equals(code)) {
				prov = p;
				break;
			}
		}
		if (prov!=null) {
			int result = JOptionPane.YES_OPTION;
			if (confirmar) result = JOptionPane.showConfirmDialog(this, 
							"El código postal corresponde a otra provincia, ¿Quiere cambiar la provincia?", 
							"Cambio de provincia", JOptionPane.YES_NO_OPTION);
		    if (result==JOptionPane.YES_OPTION) {
		    	setSelectedItem(prov);
		    }
		}
	}
	
	private boolean isValidCode(String code) {
		int c=0;
		try {
			c = new Integer(NumberFormat.getIntegerInstance().parse(code).intValue());
		} catch (ParseException e) {
			return false;
		}
		if (c!=0) return isValidCode(c);
		return false;
	}
	
	private boolean isValidCode(int code) {
		if (code<1 || code>52) return false;
		return true;
	}


}
