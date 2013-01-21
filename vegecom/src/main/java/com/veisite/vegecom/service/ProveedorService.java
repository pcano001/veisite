package com.veisite.vegecom.service;

import java.util.List;

import com.veisite.vegecom.dataio.DataIOException;
import com.veisite.vegecom.dataio.ObjectOutputFlow;
import com.veisite.vegecom.model.Proveedor;

public interface ProveedorService {

	public Proveedor save(Proveedor proveedor);
	
	public Proveedor getById(Long id);

	public List<Proveedor> getList();
	
	public void writeListTo(ObjectOutputFlow<Proveedor> output) throws DataIOException;

}
