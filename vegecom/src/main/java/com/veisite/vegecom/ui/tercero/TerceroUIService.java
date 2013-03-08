package com.veisite.vegecom.ui.tercero;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.veisite.vegecom.ui.framework.module.UIFrameworkModule;
import com.veisite.vegecom.ui.framework.service.UIFrameworkAbstractService;

public abstract class TerceroUIService<T> extends UIFrameworkAbstractService {

	/**
	 * Contexto de aplicación
	 */
	protected ApplicationContext context;
	
	
	public TerceroUIService(UIFrameworkModule uiModule, ApplicationContext context) {
		super(uiModule);
		Assert.notNull(context);
		this.context = context;
	}
	
}
