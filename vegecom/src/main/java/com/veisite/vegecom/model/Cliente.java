package com.veisite.vegecom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.filefilter.FalseFileFilter;

import com.veisite.vegecom.util.EqualsUtil;
import com.veisite.vegecom.util.HashCodeUtil;

@Entity
public class Cliente extends ModelObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=20)
    private String cif;
    
    @Column @NotNull
    private String nombre;

    @Column
	private String domicilio;

    @Column(length=5)
	private String codigoPostal;

    @ManyToOne
    private Municipio municipio;
    
    @Column
    private String localidad;
    
    @ManyToOne
    private Provincia provincia;
    
    @Column
    private String telefono;
    
    @Column
    private String cuentaBancaria;
    
    @Column
    private String email;

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
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif the cif to set
	 */
	public void setCif(String newCif) {
		String oldValue = this.cif;
		this.cif = newCif;
		pcs.firePropertyChange("cif", oldValue, newCif );
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
	public void setNombre(String newNombre) {
		String oldValue = this.nombre;
		this.nombre = newNombre;
		pcs.firePropertyChange("nombre", oldValue, newNombre );
	}
	
	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		pcs.firePropertyChange("domicilio", this.domicilio, this.domicilio = domicilio);
	}


	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		pcs.firePropertyChange("codigoPostal", this.codigoPostal,
				this.codigoPostal = codigoPostal);
	}

	/**
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		pcs.firePropertyChange("municipio", this.municipio,
				this.municipio = municipio);
	}

	/**
	 * @return the localidad
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(String localidad) {
		pcs.firePropertyChange("localidad", this.localidad,
				this.localidad = localidad);
	}

	/**
	 * @return the provincia
	 */
	public Provincia getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(Provincia provincia) {
		pcs.firePropertyChange("provincia", this.provincia,
				this.provincia = provincia);
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		pcs.firePropertyChange("telefono", this.telefono, this.telefono = telefono);
	}

	/**
	 * @return the cuentaBancaria
	 */
	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	/**
	 * @param cuentaBancaria the cuentaBancaria to set
	 */
	public void setCuentaBancaria(String cuentaBancaria) {
		pcs.firePropertyChange("cuentaBancaria", this.cuentaBancaria,
				this.cuentaBancaria = cuentaBancaria);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		pcs.firePropertyChange("email", this.email, this.email = email);
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
	    if ( !(that instanceof Cliente) ) return false;
	    Cliente o = (Cliente) that;
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