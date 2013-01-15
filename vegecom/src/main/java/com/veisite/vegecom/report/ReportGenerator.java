package com.veisite.vegecom.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.events.Event;
import org.apache.fop.events.EventListener;
import org.apache.fop.events.model.EventSeverity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStreamException;
import com.veisite.vegecom.Application;
import com.veisite.vegecom.xml.XStreamGenerator;

/**
 * Clase que genera un informe en pdf con los parametros pasados Genera el xml y
 * lo inserta en un pipe para hacer la transformacion xslt y enviar el pdf por
 * el stream de salida.
 * 
 * La generación del informe se hace utilizando dos threads y haciendo un
 * reporte del progreso en el gui si asi se requiere
 * 
 * @author josemaria
 * 
 */
public class ReportGenerator {

	private static Logger logger = LoggerFactory
			.getLogger(ReportGenerator.class);

	private Report report;

	private Throwable lastException = null;

	private Throwable lastXMLException = null;
	
	private File file = null;

	public ReportGenerator(Report report) {
		this.report = report;
	}

	/**
	 * @return Devuelve false si hubo algun error en la incialización y despacho
	 *         del informe Los errores dentro se lanzaran en los eventos de
	 *         progreso. La salida OutputStream que se le pasa no es cerrada y
	 *         deberá ser cerrada desde la parte llamante.
	 */
	public boolean generateReport(Object object, OutputStream outStream, final PageGenerationListener listener) {
		lastException = null;
		lastXMLException = null;

		// Activamos la salida XML
		PipedOutputStream outPipe = new PipedOutputStream(); 
		XStreamGenerator xmlGenerator = new XStreamGenerator(outPipe);
		
		try {
			InputStream xslt;
			// Obtener transformacion xsl
			try {
				xslt = report.getXslInputStream();
			} catch (MalformedURLException mue) {
				lastException = new ReportException("Error al obtener formato de informe. xsl",mue);
				return false;
			} catch (IOException ioe) {
				lastException = new ReportException("Error al obtener formato de informe. xsl",ioe);
				return false;
			} catch (ReportException e) {
				lastException = e;
				return false;
			}
			
			// Setup FOP
			ReportFopFactory fopFactory = Application.getContext().getBean(ReportFopFactory.class);
			if (fopFactory==null) {
				lastException = new ReportException("Error al obtener constructor de informes, ReportFopFactory");
				return false;
			}
			
			// Configurar eventos y datos del informe.
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			// gestión de eventos de generación
			if (listener!=null) {
				userAgent.getEventBroadcaster().addEventListener(new EventListener() {
					@Override
					public void processEvent(Event arg0) {
						if (arg0.getSeverity()==EventSeverity.INFO) {
							Object d = arg0.getParam("number");
							if (d!=null && d instanceof Number) {
								int pageNumber = ((Number) d).intValue();
								listener.pageGenerated(pageNumber);
							}
						}
					}
				});
			}
			
			// Obtener objeto FOP
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
			
			// Activamos la transformacion xslt
			TransformerFactory factory = TransformerFactory.newInstance();
			Source xsltSource = new StreamSource(xslt);
			Transformer transformer = factory.newTransformer(xsltSource);

			// Conectamos la salida del generador XML a la entrada de la transformacion
			PipedInputStream inPipe = new PipedInputStream(outPipe);
			Source src = new StreamSource(inPipe);

			// Configuramos la salida de la transformacion al generador FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Hacemos la transformacion XML y escuchamos errores
			lastXMLException = null;
			XmlGeneratorThread t1 = new XmlGeneratorThread(xmlGenerator, object);
			t1.start();
			
			try {
				transformer.transform(src, res);
			} catch (TransformerException te) {
				if (lastXMLException!=null) lastException=lastXMLException;
				else lastException = te;
				if (lastXMLException!=null)	logger.error("Error al generar el informe. XML generator error.",lastXMLException);
				else logger.error("Error al generar el informe.",te);
			} 
			
			// Si ha habido excepciones notificar y devolver false;
			if (lastXMLException!=null || lastException!=null) {
				return false;
			}
		} catch (FOPException e) {
			lastException = e;
			logger.error("Error al generar el informe",e);
			return false;
		} catch (TransformerConfigurationException e) {
			lastException = e;
			logger.error("Error al generar el informe",e);
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			lastException = e;
			logger.error("Error al generar el informe",e);
			return false;
		} catch (IOException e) {
			lastException = e;
			logger.error("Error al generar el informe",e);
			return false;
		}
		return true;
	}

	/**
	 * @return Devuelve false si hubo algun error en la incialización y despacho
	 *         del informe Los errores dentro se lanzaran en los eventos de
	 *         progreso. La salida OutputStream que se le pasa no es cerrada y
	 *         deberá ser cerrada desde la parte llamante.
	 */
	public boolean generateReportToFile(Object object, final PageGenerationListener listener) {
		if (file!=null) file.delete();
		
		try {
			file = File.createTempFile("gaslabreport", ".pdf" );
			logger.debug("Creando fichero temporal '{}'",file.getAbsolutePath());
			file.deleteOnExit();
		} catch (IOException ex) {
			logger.error("Error creando fichero temporal");
			lastException = new ReportException("Error creando fichero pdf temporal", ex);
			return false;
		}
		
		BufferedOutputStream outStream=null;
		try {
			outStream = new BufferedOutputStream(new FileOutputStream(file));
			return generateReport(object, outStream, listener);
		} catch (FileNotFoundException fe) {
			logger.error("Error, no se encuentra fichero temporal");
			lastException = new ReportException("Error creando fichero pdf temporal", fe);
			return false;
		} finally {
			try {
				if (outStream!=null) outStream.close();
			} catch (IOException ioe) {}
		}
	}

	/**
	 * @return the lastException
	 */
	public Throwable getLastException() {
		return lastException;
	}
	
	public Throwable getLastXMLException() {
		return lastXMLException; 
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	private class XmlGeneratorThread extends Thread {

		private XStreamGenerator generator;
		private Object renderObject;

		public XmlGeneratorThread(XStreamGenerator generator, Object renderObject) {
			this.generator = generator;
			this.renderObject = renderObject;
		}

		public void run() {
			try {
				generator.writeXML(renderObject);
			} catch (XStreamException xe) {
				lastXMLException = xe;
				logger.error("Error en la generación de XML", xe);
				return;
			} catch (UnsupportedEncodingException e) {
				lastXMLException = e;
				logger.error(
						"Error en la generación de XML. UTF-8 no soportado", e);
				return;
			} finally {
				try {
					generator.getOutput().close();
				} catch (IOException e) {
				}
			}
		}
	}

}
