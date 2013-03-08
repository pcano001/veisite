package com.veisite.vegecom.impl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veisite.vegecom.dao.ClienteDAO;
import com.veisite.vegecom.dataio.DataIOException;
import com.veisite.vegecom.dataio.ObjectOutputFlow;
import com.veisite.vegecom.model.Cliente;
import com.veisite.vegecom.service.ClienteService;

@Service
public class ClienteServiceImpl extends TerceroServiceImpl<Cliente> implements ClienteService {

	@Autowired
	ClienteDAO dao;
	
	@Override @Transactional
	public Cliente save(Cliente cliente) {
		boolean newItem = (cliente.getId()==null);
		Cliente i = dao.save(cliente);
		if (newItem) fireItemAddedEvent(i);
		else fireItemChangedEvent(i);
		return i;
	}

	@Override @Transactional
	public void remove(Cliente cliente) {
		dao.remove(cliente);
		fireItemRemovedEvent(cliente);
	}

	@Override @Transactional
	public Cliente getById(Long id) {
		return dao.getById(id);
	}

	@Override @Transactional
	public List<Cliente> getList() {
		return dao.getList();
	}
	
	@Override @Transactional
	public void writeListTo(ObjectOutputFlow<Cliente> output) throws DataIOException {
		dao.writeListTo(output);
	}

}
