package com.veisite.vegecom.ui.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UIResources {
	
	private static Logger logger = LoggerFactory.getLogger(UIResources.class);
	
	private static String iconsPath = "images/icons/";  

	public static JPanel getEditComponent(String label, JComponent c,
			boolean negrita, boolean vertical) {
		JPanel p = new JPanel(new BorderLayout());
		JLabel jlabel = new JLabel(label);
		Font f = jlabel.getFont();
		int i = f.getSize();
		if (negrita)
			jlabel.setFont(new Font(f.getName(), Font.BOLD, i));
		Dimension df = new Dimension(jlabel.getPreferredSize());
		if (vertical) {
			df.height = i + 5;
			jlabel.setAlignmentY(1.0F);
			jlabel.setAlignmentX(0.0F);
		} else {
			df.width += 5;
			jlabel.setAlignmentY(1.0F);
			jlabel.setAlignmentX(0.0F);
		}
		Dimension dc = c.getPreferredSize();
		Dimension dp = new Dimension(dc);
		if (vertical) {
			dp.width = Math.max(dc.width, df.width);
			dp.height = dc.height + df.height;
		} else {
			dp.height = Math.max(dc.height, df.height);
			dp.width = dc.width + df.width;
			df.height = dp.height;
		}
		jlabel.setMinimumSize(df);
		jlabel.setMaximumSize(df);
		jlabel.setPreferredSize(df);
		p.setMinimumSize(dp);
		p.setMaximumSize(dp);
		p.setPreferredSize(dp);
		if (vertical) {
			p.add(jlabel, BorderLayout.NORTH);
			p.add(c, BorderLayout.CENTER);
		} else {
			p.add(jlabel, BorderLayout.WEST);
			p.add(c, BorderLayout.CENTER);
		}
		return p;
	}

	public static void centerWindow(java.awt.Window window) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		window.setLocation((screenWidth-window.getWidth()) / 2, (screenHeight-window.getHeight()) / 2);
	}
	
	private static ImageIcon getIcon(String name, String size) {
		String path = iconsPath + name + size + ".png";
		URL url = path.getClass().getResource(path);
		if (url != null) return new ImageIcon(url);
		url = path.getClass().getResource("/"+path);
		if (url != null) return new ImageIcon(url);
		ClassLoader loader = UIResources.class.getClassLoader();
		if(loader != null) url = loader.getResource(path);
		if(url == null) url = loader.getResource("/"+path);
		if(url != null) return new ImageIcon(url);
		logger.error("El icono solicitado no se encuentra: {}",path);
		return null;
	}
	
	public static ImageIcon getIcon16(String name) {
		return getIcon(name,"16");
	}
	
	public static ImageIcon getIcon24(String name) {
		return getIcon(name,"24");
	}

	public static ImageIcon getIcon32(String name) {
		return getIcon(name,"32");
	}
	
	public static ImageIcon getIcon48(String name) {
		return getIcon(name,"48");
	}
	
	public static ImageIcon getIcon64(String name) {
		return getIcon(name,"64");
	}
	
	public static ImageIcon getIcon128(String name) {
		return getIcon(name,"128");
	}
	
	/**
	 * Metodo que devuelve un panel con un icono y mensaje de error
	 * Se utiliza cuando se detecta un error en la inicialización de algun
	 * componente grafico.
	 * 
	 * @return
	 */
	public static JPanel getErrorPanel(String mensaje) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    p.add(new JLabel(UIResources.getIcon48("face-sick")));
	    JLabel label = new JLabel((mensaje==null ? "Ha ocurrido un error en la aplicación." : mensaje));
	    Font f = new Font(label.getFont().getName(),Font.BOLD,label.getFont().getSize()+3);
	    label.setFont(f);
	    p.add(label);
		return p;
	}
	
	
	/**
	 * Metodo que toma una excepción y la muestra en un ventana.
	 * 
	 */
	public static void showException(Component owner, Throwable t) {
		ErrorInfo err = new ErrorInfo("Error", t.getMessage(), t.getCause().getMessage(), null, t, null, null);
		JXErrorPane.showDialog(owner, err);
	}
	
}
