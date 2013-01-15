package com.veisite.vegecom;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class ApplicationProperties {
	
	private static final String PROPERTY_EXAMPLE = "com.veisite.vegecom.EXAMPLE";

	private final ReloadableResourceBundleMessageSource bean;
	
	public ApplicationProperties(ReloadableResourceBundleMessageSource bean) {
		this.bean = bean;
	}
	
	private String getProperty(String property) {
		return bean.getMessage(property, new Object[]{}, null);
	}

	public String getExample() {
		return getProperty(PROPERTY_EXAMPLE);
	}
	
}
