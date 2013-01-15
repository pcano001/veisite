package com.veisite.vegecom.ui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.veisite.vegecom.Application;
import com.veisite.vegecom.ApplicationProperties;
import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.ui.util.UIResources;


public class DeskApp extends Application {
	
	private static Logger logger = LoggerFactory.getLogger(DeskApp.class);
	
	private static JFrame mainFrame=null;
	
	private static boolean isConfigurationLoaded = false;
	
	private static Throwable configurationException = null;

	public static void main(String[] args) {

		// Lanzamos la carga de la configuración.
		try {
		  loadConfiguration();
		  logger.info("Configuration loaded ");
		  isConfigurationLoaded = true;
		} catch (BeansException be) {
		  logger.error("Error initializing application context: SPRING",be);
		  configurationException = be;
		} 

		/* 
		 * iniciamos la interfaz de usuario
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
				    runDesktopApp();
				} catch (Throwable t) {
					logger.error("Unexpected error",t);
					ErrorInfo err = new ErrorInfo("Error", "Error inesperado", t.getMessage(), null, t, null, null);
					JXErrorPane.showDialog(null, err);
					exitDesktopApp(1);
				}
			}
		});
		
	}
	
	private static void connectDatabase() {
//	    LoginUserService lus = getContext().getBean(LoginUserService.class);
//	    lus.getById("inexistent");
	}


	public static void loadConfiguration() {
		final String[] configSpringPaths = {"META-INF/spring/applicationContext.xml"};
		/* Iniciamos contexto de aplicacion con spring */
		setContext(new ClassPathXmlApplicationContext(configSpringPaths));
		/* Inicializamos recurso de mensajes */
		setResourceBundle((ResourceBundleMessageSource) getContext().getBean("clientMessageSource"));
		/* Inicializamos propiedades */
		ReloadableResourceBundleMessageSource bean = (ReloadableResourceBundleMessageSource) getContext().getBean("clientConfiguration");
		setApplicationProperties(new ApplicationProperties(bean));
	}

	
	private static void runDesktopApp() throws Throwable {
		if (!isApplicationReady())
			throw new VegecomException("La aplicación no puede continuar. La configuración necesaria no está preparada"); 

		logger.debug("Initializing main window");
		ApplicationFrame frame=null;
		try {
			frame = new ApplicationFrame();
		} catch (VegecomException e1) {
			logger.error("Error creating main window...",e1);
			JOptionPane.showMessageDialog(null,
					"Se ha producido un error al intentar mostrar la ventana principal.",
					"Aplicación Ayudas Socialaborales",
					JOptionPane.ERROR_MESSAGE);
			exitDesktopApp(1);
		}
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.callOnDispose(new Runnable() {
			@Override
			public void run() {
				exitDesktopApp(0);
			}
		});
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
			}
			@Override
			public void windowClosed(WindowEvent e) {
			}
			@Override
			public void windowIconified(WindowEvent e) {
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			@Override
			public void windowActivated(WindowEvent e) {
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
		frame.pack();
		UIResources.centerWindow(frame);
		DeskApp.mainFrame = frame;
		frame.setVisible(true);
	}


	private static void exitDesktopApp(int exitCode) {
		logger.info("Closing application with exit code {}", exitCode);
		System.exit(exitCode);
	}

	/**
	 * Metodo que devuelve false o una excepcion si la aplicación
	 * no está operativa por algún motivo
	 * True si la aplicacion está lista para su uso y tiene
	 * conexión con la base de datos.
	 * 
	 * @throws Throwable
	 */
	public static boolean isApplicationReady() throws Throwable {
		if (!isConfigurationLoaded && configurationException!=null)
			throw configurationException;
		if (isConfigurationLoaded) connectDatabase();
		return isConfigurationLoaded;
	}
	
	/**
	 * Metodo que hace una espera hasta que la aplicacion está lista,
	 * cuando el metodo isApplicatinReady devuelve true
	 * 
	 * Hace una espera máxima aproximada de 20 segundos. Si despues de 
	 * ese tiempo no está lista lanza una excepción
	 * 
	 * @throws Throwable
	 */
	public static void waitForApplicationReady() throws Throwable {
		Object monitor = new Object();
		int timeout = 20000;
		long itime = System.currentTimeMillis();
		synchronized (monitor) {
			do {
				if (isApplicationReady()) return;
				monitor.wait(100L);
			} while (System.currentTimeMillis()-itime < timeout);
		}
		throw new VegecomException("El tiempo de inicializacion de la aplicación es excesivo");
	}
	

	public static Frame getMainFrame() {
		return mainFrame;
	}

	public static String getMessage(String code, Object[] args, String defaultMessage) {
		return getResourceBundle().getMessage(code, args, defaultMessage, Locale.getDefault());
	}

}
