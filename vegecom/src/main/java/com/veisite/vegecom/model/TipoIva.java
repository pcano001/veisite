package com.veisite.vegecom.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.veisite.vegecom.util.EqualsUtil;
import com.veisite.vegecom.util.HashCodeUtil;

@Entity
public class TipoIva extends ModelObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column @NotNull
    private String nombre;
    
    @OneToMany
    private List<TramoTiposIva> tramoTiposIva;
    
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		pcs.firePropertyChange("nombre", this.nombre, this.nombre = nombre);
	}
	
	/**
	 * @return the tramoTiposIva
	 */
	public List<TramoTiposIva> getTramoTiposIva() {
		return tramoTiposIva;
	}

	
	/**
	 * toString
	 */
	public String toString() {
		return getNombre();
	}

	/**
	 * equals
	 */
	@Override 
	public boolean equals(Object that) {
	    if ( this == that ) return true;
	    if ( !(that instanceof TipoIva) ) return false;
	    TipoIva o = (TipoIva) that;
	    return (EqualsUtil.areEqual(this.id,o.id) && EqualsUtil.areEqual(this.nombre,o.nombre));
	}

	/**
	 * hashCode
	 */
	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, id);
		result = HashCodeUtil.hash(result, nombre);
		return result;
	}

	
}
