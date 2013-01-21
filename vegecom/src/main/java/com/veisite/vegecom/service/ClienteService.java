package com.veisite.vegecom.service;

import java.util.List;

import com.veisite.vegecom.dataio.DataIOException;
import com.veisite.vegecom.dataio.ObjectOutputFlow;
import com.veisite.vegecom.model.Cliente;

public interface ClienteService {

	public Cliente save(Cliente cliente);
	
	public Cliente getById(Long id);

	public List<Cliente> getList();
	
	public void writeListTo(ObjectOutputFlow<Cliente> output) throws DataIOException;

}
