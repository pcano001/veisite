package com.veisite.vegecom.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Proveedor extends TerceroComercial {


	/**
	 * serial
	 */
	private static final long serialVersionUID = -792277261858752284L;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor", orphanRemoval = true)
	private List<PrecioArticuloProveedor> preciosArticulos;
	

	/**
	 * @return the preciosArticulos
	 */
	public List<PrecioArticuloProveedor> getPreciosArticulos() {
		return preciosArticulos;
	}

}
