package com.veisite.vegecom.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.ui.components.tabbedpane.CloseableTabbedPane;


public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = -1576129999367062116L;

	private Runnable callOnDispose = null;

	private CloseableTabbedPane tabbedPane = new CloseableTabbedPane();
	
	public static final Dimension MAIN_JFRAME_PREFDIMENSION = new Dimension(940, 680);
	
	/**
	 * Menus
	 * 
	 */
	private JMenuBar jMenuBar;
	private JMenu jMenuFile;
	
	public ApplicationFrame() throws VegecomException {
		super();
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		String title = DeskApp.getMessage("ui.ApplicationFrame.Title", null, "Gestión Comercial");
		if (!DeskApp.isProductionMode()) title += " (Runnig in test mode)";
		setTitle(title);
		initComponent();
	}

	private void initComponent() throws VegecomException {
		setLayout(new BorderLayout());
		createMenuBar();
		configureMenuBar();
		add(tabbedPane,BorderLayout.CENTER);
		setPreferredSize(MAIN_JFRAME_PREFDIMENSION);
	}

	private void createMenuBar() {
		jMenuBar = new JMenuBar();
		
		jMenuFile=new JMenu();
		jMenuFile.setText(DeskApp.getMessage("ui.ApplicationFrame.Menu.File", null, "Archivo"));
		jMenuFile.add(new ExitAction());
		jMenuBar.add(jMenuFile);
		
		setJMenuBar(jMenuBar);
	}
	
	private void configureMenuBar() {
	}

	public void callOnDispose(Runnable task) {
		this.callOnDispose = task;
	}
	
	public void dispose() {
		super.dispose();
		if (callOnDispose!=null) callOnDispose.run();
	}
	
	private class ExitAction extends AbstractAction {
		private static final long serialVersionUID = 7888796965011554614L;
		ExitAction() {
			super(DeskApp.getMessage("ui.ApplicationFrame.Menu.File.Exit", null, "Salir"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			ApplicationFrame.this.dispose();
		}
	}
		
}