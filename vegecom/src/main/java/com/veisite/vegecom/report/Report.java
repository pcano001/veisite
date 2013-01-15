package com.veisite.vegecom.report;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase que contiene la definión de informe 
 * Tiene un identificador
 * @author josemaria
 *
 */
public class Report {
	
	@Autowired
	ReportFopFactory reportFactory;
	
	private static Logger logger = LoggerFactory.getLogger(Report.class);
	
	private String id;
	
	private String xslTemplate;

	public Report() {
		
	}
	
	public Report(String id, String xslTemplate) {
		this.id = id;
		this.xslTemplate = xslTemplate;
	}
	
	/**
	 * Devuelve el punto de entrada de recuperacion del formato xsl
	 */ 
	public InputStream getXslInputStream() throws MalformedURLException, IOException, ReportException {
		if (reportFactory==null) {
			logger.error("reportFactory==null, no funcionó @Autowired");
			throw new ReportException("No fue posible encontrar el formato del informe. reportFactory==null");
		}
		String surl = reportFactory.getXlsTemplateBase()+xslTemplate;
		URL url;
		InputStream inXsl;
		url = new URL(surl);
		inXsl = url.openStream();
		return inXsl;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the xslTemplate
	 */
	public String getXslTemplate() {
		return xslTemplate;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param xslTemplate the xslTemplate to set
	 */
	public void setXslTemplate(String xslTemplate) {
		this.xslTemplate = xslTemplate;
	}

}
