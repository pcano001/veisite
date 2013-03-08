package com.veisite.vegecom.ui.framework.module;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.ui.framework.UIFrameworkInstance;
import com.veisite.vegecom.ui.framework.UIFrameworkObject;

public class UIFrameworkModuleManager implements UIFrameworkObject {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * Framework sobre el que trabaja el gestor de modulos
	 */
	private UIFrameworkInstance uiInstance;
	
	/**
	 * Contexto de aplicación
	 */
	private ApplicationContext context;
	
	
	/**
	 * Lista de modulos que tenemos cargados e iniciados 
	 * 
	 * @param id
	 */
	List<UIFrameworkModule> moduleList = new ArrayList<UIFrameworkModule>();

	
	public UIFrameworkModuleManager(String id) {
		this(id, null, null);
	}
	
	public UIFrameworkModuleManager(String id, UIFrameworkInstance uiInstance, ApplicationContext context) {
		this.id = id;
		this.setUiInstance(uiInstance);
		this.setContext(context);
	}
	
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @return the uiInstance
	 */
	public UIFrameworkInstance getUiInstance() {
		return uiInstance;
	}

	/**
	 * @param uiInstance the uiInstance to set
	 */
	public void setUiInstance(UIFrameworkInstance uiInstance) {
		this.uiInstance = uiInstance;
	}

	/**
	 * @return the context
	 */
	public ApplicationContext getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	
	public void installModule(UIFrameworkModule module) throws Throwable {
		// Si no tenemos frmework o contexto lanzar excepcion
		if (uiInstance==null)
			throw new VegecomException("UI framework is null. Cannot install modules");
		if (context==null)
			throw new VegecomException("Application context is null. Cannot install modules");
		logger.debug("Trying to install module with id "+module.getId());
		try {
			module.initModule(uiInstance, context);
		} catch (Throwable t) {
			logger.error("Error installing module with id "+module.getId(),t);
			throw t;
		}
		logger.debug("module with id "+module.getId()+" installed");
		moduleList.add(module);
	}

	/**
	 * Desintala un modulo
	 * 
	 * @param id
	 * @throws VegecomException
	 */
	public void uninstallModule(String id) throws Throwable {
		UIFrameworkModule module = getModule(id);
		if (module!=null) uninstallModule(module);
	}
	
	public void uninstallModule(UIFrameworkModule module) throws Throwable {
		logger.debug("Trying to uninstall module with id "+module.getId());
		int i = moduleList.indexOf(module);
		if (i>=0) {
			try {
				module.disposeModule();
			} catch (Throwable t) {
				logger.error("Error uninstalling module with id "+module.getId(),t);
				throw t;
			}
			moduleList.remove(i);
			logger.debug("module with id "+module.getId()+" uninstalled");
		}
	}
	
	
	private UIFrameworkModule getModule(String id) {
		for (UIFrameworkModule m : moduleList)
			if (m.getId().equals(id)) return m;
		return null;
	}
}
