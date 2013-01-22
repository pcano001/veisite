package com.veisite.vegecom.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends TerceroComercial {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
	private List<PrecioArticuloCliente> preciosArticulos;
    

	/**
	 * @return the tarifas
	 */
	public List<PrecioArticuloCliente> getPreciosArticulos() {
		return preciosArticulos;
	}

}
