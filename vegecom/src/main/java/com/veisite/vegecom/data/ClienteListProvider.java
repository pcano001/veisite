package com.veisite.vegecom.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veisite.vegecom.Application;
import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.service.ClienteService;
import com.veisite.vegecom.service.TerceroService;

public class ClienteListProvider extends TerceroListProvider<Cliente> {

	private static final Logger logger = LoggerFactory.getLogger(ClienteListProvider.class);
	
	/**
	 * servicio de recuperacion de clientes 
	 */
	private ClienteService dataService;
	
	/**
	 * Constructor
	 */
	public ClienteListProvider() throws VegecomException {
		dataService = Application.getContext().getBean(ClienteService.class);
		if (dataService==null) {
			logger.error("Error getting ClienteService from spring context... bean is null.");
			String m = Application.getMessage("data.ClienteListProvider.ServiceBeanNull", 
						null, "Cannot get ClienteService bean.");
			throw new VegecomException(m);
		}
	}
	
	@Override
	protected TerceroService<Cliente> getDataService() {
		return dataService;
	}

}
