package com.veisite.vegecom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.veisite.vegecom.util.EqualsUtil;
import com.veisite.vegecom.util.HashCodeUtil;

@Entity
public class FamiliaArticulo extends ModelObject {

    /**
	 * serial
	 */
	private static final long serialVersionUID = -2268030425418466164L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column 
    @NotNull @NotEmpty
    private String nombre;

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
	    if ( !(that instanceof FamiliaArticulo) ) return false;
	    FamiliaArticulo o = (FamiliaArticulo) that;
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
