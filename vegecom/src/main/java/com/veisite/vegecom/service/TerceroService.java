package com.veisite.vegecom.service;

import java.util.List;

import com.veisite.vegecom.dataio.DataIOException;
import com.veisite.vegecom.dataio.ObjectOutputFlow;
import com.veisite.vegecom.model.TerceroComercial;

public abstract interface TerceroService<T extends TerceroComercial> {

	public T save(T terceroComercial);
	
	public void remove(T terceroComercial);
	
	public T getById(Long id);

	public List<T> getList();
	
	public void writeListTo(ObjectOutputFlow<T> output) throws DataIOException;

}
