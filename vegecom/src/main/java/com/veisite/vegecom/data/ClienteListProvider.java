package com.veisite.vegecom.data;

import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.service.ClienteService;
import com.veisite.vegecom.service.TerceroService;

public class ClienteListProvider extends TerceroListProvider<Cliente> {

	/**
	 * servicio de recuperacion de clientes 
	 */
	private ClienteService dataService;
	
	/**
	 * Constructor
	 */
	public ClienteListProvider(ClienteService dataService) {
		this.dataService = dataService;
	}
	
	@Override
	protected TerceroService<Cliente> getDataService() {
		return dataService;
	}

}
