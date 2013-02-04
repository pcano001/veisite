package com.veisite.vegecom.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veisite.vegecom.Application;
import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.model.Proveedor;
import com.veisite.vegecom.service.ProveedorService;
import com.veisite.vegecom.service.TerceroService;

public class ProveedorListProvider extends TerceroListProvider<Proveedor> {

	private static final Logger logger = LoggerFactory.getLogger(ProveedorListProvider.class);
	
	/**
	 * servicio de recuperacion de clientes 
	 */
	private ProveedorService dataService;
	
	/**
	 * Constructor
	 */
	public ProveedorListProvider() throws VegecomException {
		dataService = Application.getContext().getBean(ProveedorService.class);
		if (dataService==null) {
			logger.error("Error getting ProveedorService from spring context... bean is null.");
			String m = Application.getMessage("data.ProveedorListProvider.ServiceBeanNull", 
						null, "Cannot get ProveedorService bean.");
			throw new VegecomException(m);
		}
	}
	
	@Override
	protected TerceroService<Proveedor> getDataService() {
		return dataService;
	}

}
