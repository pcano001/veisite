package com.veisite.vegecom;

import java.util.Locale;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Singleton que mantiene objetos de configuracion de la aplicacion
 * asi como el acceso al contexto Spring
 *
 */
public class Application {

	/**
	 * aplicationContext
	 */
	private static ClassPathXmlApplicationContext context;
	
	/**
	 * Message resource
	 */
	private static ResourceBundleMessageSource resourceBundle;
	
	/**
	 * Application global properties
	 */
	private static ApplicationProperties aplicationProperties;
	
	/**
	 * @return the context
	 */
	public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public static void setContext(ClassPathXmlApplicationContext context) {
		Application.context = context;
	}

	/**
	 * @return the resourceBundle
	 */
	public static ResourceBundleMessageSource getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * @param resourceBundle the resourceBundle to set
	 */
	public static void setResourceBundle(ResourceBundleMessageSource resourceBundle) {
		Application.resourceBundle = resourceBundle;
	}

	public static ApplicationProperties getAplicationProperties() {
		return aplicationProperties;
	}

	public static void setApplicationProperties(ApplicationProperties applicationProperties) {
		Application.aplicationProperties = applicationProperties;
	}

	/**
	 * Devuelve mensaje de recurso. 
	 * 
	 * @param code
	 * @param args
	 * @param defaultMessage
	 * @return
	 */
	public static String getMessage(String code, Object[] args, String defaultMessage) {
		return getResourceBundle().getMessage(code, args, defaultMessage, Locale.getDefault());
	}
	
}
