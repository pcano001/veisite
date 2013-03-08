package com.veisite.vegecom.ui.framework.module;

import org.springframework.context.ApplicationContext;

import com.veisite.vegecom.ui.framework.UIFrameworkInstance;
import com.veisite.vegecom.ui.framework.UIFrameworkObject;

/**
 * Interfaz que debe implementar un modulo para poder 
 * integrarse en el framework grafico
 * 
 * @author josemaria
 *
 */
public interface UIFrameworkModule extends UIFrameworkObject {
	
	public void initModule(UIFrameworkInstance uiInstance, ApplicationContext context) throws Throwable;
	
	public void disposeModule() throws Throwable;

}
