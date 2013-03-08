package com.veisite.vegecom.ui.tercero.cliente;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.service.ClienteService;
import com.veisite.vegecom.ui.DeskApp;
import com.veisite.vegecom.ui.VegecomUIMenu;
import com.veisite.vegecom.ui.framework.UIFrameworkInstance;
import com.veisite.vegecom.ui.framework.menu.UIFrameworkMenu;
import com.veisite.vegecom.ui.framework.menu.UIFrameworkMenuBar;
import com.veisite.vegecom.ui.framework.menu.UIFrameworkMenuItem;
import com.veisite.vegecom.ui.framework.module.UIFrameworkModule;
import com.veisite.vegecom.ui.framework.views.UIFrameworkView;
import com.veisite.vegecom.ui.util.UIResources;

public class ClienteUIModule implements UIFrameworkModule {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * module id
	 */
	public static String MODULE_ID = "clientesModule";
	
	/**
	 * Servicio de acceso a datos de cliente 
	 */
	private ClienteService clienteService=null;
	
	/**
	 * Framework en el que colabora
	 */
	private UIFrameworkInstance uiInstance;
	
	/**
	 * Contexto de la aplicación
	 */
	private ApplicationContext context;
	
	
	/**
	 * Recursos graficos del modulo.
	 */
	private UIFrameworkView clienteListView = null;

	@Override
	public String getId() {
		return MODULE_ID;
	}

	@Override
	public void initModule(UIFrameworkInstance uiInstance,
			ApplicationContext context) throws Throwable {
		this.uiInstance = uiInstance;
		this.context = context;
		try {
			clienteService = this.context.getBean(ClienteService.class);
		} catch (Throwable t) {
			logger.error("Cannot get Customer Service",t);
			throw t;
		}
		if (clienteService==null) 
			throw new VegecomException("Cannot get Customer Service. Module cannot init");
		configureUI();
	}

	@Override
	public void disposeModule() throws Throwable {
	}

	/**
	 * Configura los elementos graficos que se integran en el framework
	 */
	private void configureUI() {
		UIFrameworkMenuBar menuBar = uiInstance.getUIFrameworkMenuBar();
		UIFrameworkMenu menu = menuBar.getMenu(VegecomUIMenu.MENU_SELL_ID);
		if (menu==null) {
			String m = DeskApp.getMessage("ui.ApplicationFrame.Menu.Sells", null, "Sells");
			menu = menuBar.addMenu(VegecomUIMenu.MENU_SELL_ID, m);
		}
		String m = DeskApp.getMessage("ui.ClientesModule.Menu.ClientesList", null, "Customers");
		UIFrameworkMenuItem mi = new UIFrameworkMenuItem("customerList",new ClienteListAction(m));
		menu.add(mi);
	}
	
	
	
	private class ClienteListAction extends AbstractAction {
		
		/**
		 * serial
		 */
		private static final long serialVersionUID = 2006976827174012189L;

		public ClienteListAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (clienteListView==null) {
				try {
					clienteListView = new ClienteListPanel(clienteService);
				} catch (VegecomException exception) {
					logger.error("Cannot make a clienteListPanel",exception);
					UIResources.showException(uiInstance, exception);
					return;
				}
			}
			uiInstance.getViewArea().addView(clienteListView);
		}
		
	}
}
