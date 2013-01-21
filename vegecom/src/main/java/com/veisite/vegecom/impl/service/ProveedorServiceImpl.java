package com.veisite.vegecom.impl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veisite.vegecom.dao.ProveedorDAO;
import com.veisite.vegecom.dataio.DataIOException;
import com.veisite.vegecom.dataio.ObjectOutputFlow;
import com.veisite.vegecom.model.Proveedor;
import com.veisite.vegecom.service.ProveedorService;

@Service
public class ProveedorServiceImpl implements ProveedorService {

	@Autowired
	ProveedorDAO dao;
	
	@Override @Transactional
	public Proveedor save(Proveedor proveedor) {
		return dao.save(proveedor);
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
