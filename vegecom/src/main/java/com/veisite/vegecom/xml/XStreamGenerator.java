package com.veisite.vegecom.xml;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamGenerator extends XStream {
		
	private OutputStream output;
	
	public XStreamGenerator(OutputStream output) {
		super(new DomDriver());
		autodetectAnnotations(true);
	}
	
	public void writeXML(Object obj) throws UnsupportedEncodingException {
		Writer writer;
		writer = new OutputStreamWriter(output, "UTF-8");
		toXML(obj,writer);
	}

	/**
	 * @return the output
	 */
	public OutputStream getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(OutputStream output) {
		this.output = output;
	}

}
