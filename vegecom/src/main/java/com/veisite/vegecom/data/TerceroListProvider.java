package com.veisite.vegecom.data;

import com.veisite.vegecom.dataio.AbstractPipedListProvider;
import com.veisite.vegecom.dataio.ObjectOutputFlow;
import com.veisite.vegecom.model.TerceroComercial;
import com.veisite.vegecom.service.TerceroService;

public abstract class TerceroListProvider<T extends TerceroComercial> 
								extends AbstractPipedListProvider<T> {
	
	/**
	 * Servicio para la recuperación de terceros
	 */
	protected abstract TerceroService<T> getDataService();

	/**
	 * @see com.veisite.vegecom.dataio.AbstractPipedListProvider#writeOutputStream(com.veisite.vegecom.dataio.ObjectOutputFlow)
	 */
	@Override
	protected void writeOutputStream(ObjectOutputFlow<T> output)
			throws Throwable {
		getDataService().writeListTo(output);
	}
	
}
