package com.veisite.vegecom.data;

import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.service.ClienteService;

public class ClienteListProvider extends TerceroListProvider<Cliente> {

	/**
	 * Constructor
	 */
	public ClienteListProvider(ClienteService dataService) {
		super(dataService);
	}
	
}
