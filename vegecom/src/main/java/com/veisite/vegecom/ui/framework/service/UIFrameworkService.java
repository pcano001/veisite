package com.veisite.vegecom.ui.framework.service;

import org.springframework.context.ApplicationContext;

import com.veisite.vegecom.ui.framework.UIFrameworkInstance;
import com.veisite.vegecom.ui.framework.UIFrameworkObject;

public interface UIFrameworkService extends UIFrameworkObject {

	public void initService(UIFrameworkInstance uiInstance, ApplicationContext context) throws Throwable;
	
	public void disposeService();
	
}
