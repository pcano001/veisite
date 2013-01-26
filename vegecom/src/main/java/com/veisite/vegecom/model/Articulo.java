package com.veisite.vegecom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.veisite.vegecom.util.EqualsUtil;
import com.veisite.vegecom.util.HashCodeUtil;

@Entity
public class Articulo extends ModelObject {

    /**
	 * serial
	 */
	private static final long serialVersionUID = 4109582637079210188L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    @NotNull @NotEmpty 
    private String codigo;

    @Column
    @NotNull @NotEmpty
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(length=1) 
    @NotNull
    private TipoArticulo tipoArticulo;
    
    @ManyToOne
    private FamiliaArticulo familia;
    
    @Enumerated(EnumType.STRING)
    @Column(length=1) 
    @NotNull
    private UnidadMedida unidadMedida;
    
    @ManyToOne
    private TipoIva tipoIva;
    
    /**
     * Peso en kg de una unidad de producto
     */
    @Column
    private double pesoUnidad;

    /**
     * Precio general de coste por unidad
     */
    @Column 
    private double precioCoste;
    
    /**
     * Precio general de venta por unidad
     */
    @Column
    private double precioVenta;

    /**
     * Campo de observaciones generales para el artículo
     */
	@Lob
	private String observaciones;

	/**
	 * Se puede asociar un color al artículo
	 */
	@Column
	private int color;
	
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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		pcs.firePropertyChange("codigo", this.codigo, this.codigo = codigo);
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		pcs.firePropertyChange("descripcion", this.descripcion,
				this.descripcion = descripcion);
	}

	
	/**
	 * @return the tipoArticulo
	 */
	public TipoArticulo getTipoArticulo() {
		return tipoArticulo;
	}

	/**
	 * @param tipoArticulo the tipoArticulo to set
	 */
	public void setTipoArticulo(TipoArticulo tipoArticulo) {
		pcs.firePropertyChange("tipoArticulo", this.tipoArticulo, this.tipoArticulo = tipoArticulo);
	}

	/**
	 * @return the familia
	 */
	public FamiliaArticulo getFamilia() {
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia(FamiliaArticulo familia) {
		pcs.firePropertyChange("familia", this.familia, this.familia = familia);
	}

	
	/**
	 * @return the unidadMedida
	 */
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(UnidadMedida unidadMedida) {
		pcs.firePropertyChange("unidadMedida", this.unidadMedida, this.unidadMedida = unidadMedida);
	}

	/**
	 * @return the tipoIva
	 */
	public TipoIva getTipoIva() {
		return tipoIva;
	}

	/**
	 * @param tipoIva the tipoIva to set
	 */
	public void setTipoIva(TipoIva tipoIva) {
		pcs.firePropertyChange("tipoIva", this.tipoIva, this.tipoIva = tipoIva);
	}

	/**
	 * @return the pesoUnidad
	 */
	public double getPesoUnidad() {
		return pesoUnidad;
	}

	/**
	 * @param pesoUnidad the pesoUnidad to set
	 */
	public void setPesoUnidad(double pesoUnidad) {
		pcs.firePropertyChange("pesoUnidad", this.pesoUnidad, this.pesoUnidad = pesoUnidad);
	}

	/**
	 * @return the precioCoste
	 */
	public double getPrecioCoste() {
		return precioCoste;
	}

	/**
	 * @param precioCoste the precioCoste to set
	 */
	public void setPrecioCoste(double precioCoste) {
		pcs.firePropertyChange("precioCoste", this.precioCoste,
				this.precioCoste = precioCoste);
	}

	/**
	 * @return the precioVenta
	 */
	public double getPrecioVenta() {
		return precioVenta;
	}

	/**
	 * @param precioVenta the precioVenta to set
	 */
	public void setPrecioVenta(double precioVenta) {
		pcs.firePropertyChange("precioVenta", this.precioVenta,
				this.precioVenta = precioVenta);
	}

	
	/**
	 * @return the observaciones
	 */
	protected String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	protected void setObservaciones(String observaciones) {
		pcs.firePropertyChange("observaciones", this.observaciones,
				this.observaciones = observaciones);
	}

	/**
	 * toString
	 */
	public String toString() {
		return getDescripcion();
	}

	/**
	 * equals
	 */
	@Override 
	public boolean equals(Object that) {
	    if ( this == that ) return true;
	    if ( !(that instanceof Articulo) ) return false;
	    Articulo o = (Articulo) that;
	    return (EqualsUtil.areEqual(this.id,o.id) &&
	    		EqualsUtil.areEqual(this.codigo,o.codigo) &&
	    		EqualsUtil.areEqual(this.descripcion,o.descripcion)); 
	}

	/**
	 * hashCode
	 */
	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, id);
		result = HashCodeUtil.hash(result, descripcion);
		return result;
	}	
	
}
