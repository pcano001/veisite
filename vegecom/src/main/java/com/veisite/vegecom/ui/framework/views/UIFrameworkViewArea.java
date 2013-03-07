package com.veisite.vegecom.ui.framework.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.veisite.vegecom.ui.components.tabbedpane.CloseableTabbedPane;
import com.veisite.vegecom.ui.framework.UIFramework;
import com.veisite.vegecom.ui.framework.menu.UIFrameworkMenuObject;

/**
 * Componente grafico que mantiene una serie de vistas en forma
 * de paneles seleccionable (tabbedPane) con la opción de poder
 * cerrarlos.
 * 
 * @author josemaria
 *
 */
public class UIFrameworkViewArea extends CloseableTabbedPane implements UIFrameworkMenuObject {
	
	/**
	 * serial
	 */
	private static final long serialVersionUID = -2020596505332632511L;

	/**
	 * identificador 
	 * @param id
	 */
	private String id;
	
	/**
	 * priority
	 * @param priority
	 */
	private int priority = UIFramework.DEFAULT_PRIORITY;
	
	/**
	 * Lista de vistas que se muestran 
	 * @param id
	 */
	private List<UIFrameworkView> viewList = new ArrayList<UIFrameworkView>();
	
	
	public UIFrameworkViewArea(String id) {
		super();
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}
	

	/**
	 * Añade una vista al area de vista. 
	 * La seleccina por defecto
	 * 
	 * @param view
	 */
	public void addView(UIFrameworkView view) {
		if (!viewList.contains(view)) {
			viewList.add(view);
			Collections.sort(viewList, new Comparator<UIFrameworkView>() {
				@Override
				public int compare(UIFrameworkView o1,
						UIFrameworkView o2) {
					return o1.getPriority()-o2.getPriority();
				}
			});
			constructViewArea();
		}
		setSelectedComponent(view);
	}

	/**
	 * Elimina una vista del area
	 * 
	 * @param view
	 */
	public UIFrameworkView removeView(UIFrameworkView view) {
		int i = viewList.indexOf(view);
		if (i>=0) {
			UIFrameworkView v = viewList.remove(i);
			remove(view);
			return v;
		} else return null;
	}
	
	/**
	 * Devuelve la vista con el id indicado
	 */
	public UIFrameworkView getView(String id) {
		for (UIFrameworkView v : viewList)
			if (v.getId().equals(id)) return v;
		return null;
	}

	
	private void constructViewArea() {
		removeAll();
		// Vamos añadiendo las areas en orden de prioridad
		for (UIFrameworkView v : viewList) {
			addTab(v.getTitle(), v);
		}
	}
}
