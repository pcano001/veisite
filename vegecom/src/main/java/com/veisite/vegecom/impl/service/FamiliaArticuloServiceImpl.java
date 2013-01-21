package com.veisite.vegecom.impl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veisite.vegecom.dao.FamiliaArticuloDAO;
import com.veisite.vegecom.model.FamiliaArticulo;
import com.veisite.vegecom.service.FamiliaArticuloService;

@Service
public class FamiliaArticuloServiceImpl extends AbstractCacheableDataService<FamiliaArticulo> implements FamiliaArticuloService {
	
	private static final Logger logger = LoggerFactory.getLogger(FamiliaArticuloServiceImpl.class);
	
	@Autowired
	FamiliaArticuloDAO dao;

	@Override @Transactional
	public FamiliaArticulo save(FamiliaArticulo familiaArticulo) {
		invalidateCache();
		return dao.save(familiaArticulo);
	}

	@Override @Transactional
	public FamiliaArticulo getById(Long id) {
		return dao.getById(id);
	}

	@Override @Transactional
	public List<FamiliaArticulo> getList() {
		return getCacheList();
	}

	@Override
	protected List<FamiliaArticulo> loadCacheList() {
		logger.debug("Requesting FamiliaArticulo list to dao DB");
		return dao.getList();
	}

}
