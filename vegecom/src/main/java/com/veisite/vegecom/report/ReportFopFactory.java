package com.veisite.vegecom.report;

import java.io.IOException;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

public class ReportFopFactory extends FopFactory {
	
	private String xlsTemplateBase="./"; 
	
	public ReportFopFactory(String uriConfig) 
			throws ConfigurationException, SAXException, IOException {
		DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
		Configuration cfg = cfgBuilder.build(uriConfig);
		setUserConfig(cfg);
		this.setXlsTemplateBase(xlsTemplateBase);
	}

	/**
	 * @return the xlsTemplateBase
	 */
	public String getXlsTemplateBase() {
		return xlsTemplateBase;
	}

	/**
	 * @param xlsTemplateBase the xlsTemplateBase to set
	 */
	public void setXlsTemplateBase(String xlsTemplateBase) {
		this.xlsTemplateBase = xlsTemplateBase;
	}
	
}
