package com.veisite.vegecom.impl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veisite.utils.dataio.DataIOException;
import com.veisite.utils.dataio.ObjectOutputFlow;
import com.veisite.vegecom.dao.ProveedorDAO;
import com.veisite.vegecom.model.Proveedor;
import com.veisite.vegecom.service.ProveedorService;

@Service
public class ProveedorServiceImpl extends TerceroServiceImpl<Proveedor> implements ProveedorService {

	@Autowired
	ProveedorDAO dao;
	
	@Override @Transactional
	public Proveedor save(Proveedor proveedor) {
		boolean newItem = (proveedor.getId()==null);
		Proveedor i = dao.save(proveedor);
		if (newItem) fireItemAddedEvent(i);
		else fireItemChangedEvent(i);
		return i;
	}

	@Override @Transactional
	public void remove(Proveedor proveedor) {
		dao.remove(proveedor);
		fireItemRemovedEvent(proveedor);
	}

	@Override @Transactional
	public Proveedor getById(Long id) {
		return dao.getById(id);
	}

	@Override @Transactional
	public List<Proveedor> getList() {
		return dao.getList();
	}
	
	@Override @Transactional
	public void writeListTo(ObjectOutputFlow<Proveedor> output) throws DataIOException {
		dao.writeListTo(output);
	}

}
