package com.veisite.vegecom.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.ui.components.tabbedpane.CloseableTabbedPane;
import com.veisite.vegecom.ui.tercero.cliente.ClienteListPanel;
import com.veisite.vegecom.ui.util.UIResources;


public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = -1576129999367062116L;

	private Runnable callOnDispose = null;

	public static final Dimension MAIN_JFRAME_PREFDIMENSION = new Dimension(940, 680);
	
	/**
	 * Menus
	 * 
	 */
	private JMenuBar jMenuBar;
	private JMenu jMenuFile;
	private JMenu jMenuSell;
	
	/**
	 * Tabs
	 * @throws VegecomException
	 */
	private CloseableTabbedPane tabbedPane; 
	private Component clientesList=null;
	
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
		tabbedPane = new CloseableTabbedPane();
		add(tabbedPane,BorderLayout.CENTER);
		setPreferredSize(MAIN_JFRAME_PREFDIMENSION);
	}

	private void createMenuBar() {
		jMenuBar = new JMenuBar();
		
		jMenuFile=new JMenu();
		jMenuFile.setText(DeskApp.getMessage("ui.ApplicationFrame.Menu.File", null, "File"));
		jMenuFile.add(new ExitAction());
		jMenuBar.add(jMenuFile);
		jMenuSell=new JMenu();
		jMenuSell.setText(DeskApp.getMessage("ui.ApplicationFrame.Menu.Sells", null, "Sells"));
		jMenuSell.add(new ClientesAction());
		jMenuBar.add(jMenuSell);
		
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
		
	private class ClientesAction extends AbstractAction {
		/**
		 * serial
		 */
		private static final long serialVersionUID = -1764066559743693484L;
		
		ClientesAction() {
			super(DeskApp.getMessage("ui.ApplicationFrame.Menu.Sells.Clientes", null, "Customers"));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (clientesList==null) {
				try {
					clientesList = new ClienteListPanel();
				} catch (VegecomException ve) {
					String m = DeskApp.getMessage("ui.ApplicationFrame.ClientesAction.ErrorList", 
							null, "Cannot show customer list");
					UIResources.showException(tabbedPane, ve, m);
					return;
				}
			}
			if (clientesList==null) return;
			int i = tabbedPane.getTabIndex(clientesList);
			if (i>=0) tabbedPane.setSelectedIndex(i);
			else {
				String t = DeskApp.getMessage("ui.ApplicationFrame.TabbedPane.ClientesTitle", 
						null, "Customers");
				tabbedPane.addTab(t,clientesList,true);
			}
		}
	}
		
}