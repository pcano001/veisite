package com.veisite.vegecom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.veisite.vegecom.util.EqualsUtil;
import com.veisite.vegecom.util.HashCodeUtil;

@Entity
public class Articulo extends ModelObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String codigo;

    @Column
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(length=1) @NotNull
    private TipoArticulo tipoArticulo;
    
    @ManyToOne
    private FamiliaArticulo familia;
    
    @Enumerated(EnumType.STRING)
    @Column(length=1) @NotNull
    private UnidadMedida unidadMedida;
    
    @ManyToOne
    private TipoIva tipoIva;
    
    /**
     * Peso en kg de una unidad de producto
     */
    @Column
    private double pesoUnidad;
    
    /**
     * Cuando un articulo tiene varios envases, estos diferentes envases
     * se crean a su vez como artículos cuyo padre en el articulo que envasan
     * Los artículos con padre no tienen descripción ni código, heredan ambos.
     * En realidad, todas las propiedades de más arriba son heredadas. 
     * Si tienen obligatoriamene cantidad del articulo padre que contienen
     *  Si esta propiedad es nulo es un articulo por derecho propio, si no es nula
     *  es que se trata de un tipo de envasado de un producto.
     */ 
    @ManyToOne
    private Articulo articuloEnvasado;
    
    @Column
    private double cantidadEnvasada;
    
    @Column
    private double pesoEnvase;

    @Column
    private double precioEnvase;
    
    @Column 
    private double precioCoste;
    
    @Column
    private double precioVenta;
    
    @Column
    private boolean permiteStockNegativo;
    
    @Column
    private double stock;
    

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
		if (articuloEnvasado!=null) return articuloEnvasado.getCodigo();
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		if (articuloEnvasado!=null) articuloEnvasado.setCodigo(codigo);
		pcs.firePropertyChange("codigo", this.codigo, this.codigo = codigo);
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		if (articuloEnvasado!=null) return articuloEnvasado.getDescripcion();
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		if (articuloEnvasado!=null) articuloEnvasado.setDescripcion(descripcion);
		pcs.firePropertyChange("descripcion", this.descripcion,
				this.descripcion = descripcion);
	}

	
	/**
	 * @return the tipoArticulo
	 */
	public TipoArticulo getTipoArticulo() {
		if (articuloEnvasado!=null) articuloEnvasado.getTipoArticulo();
		return tipoArticulo;
	}

	/**
	 * @param tipoArticulo the tipoArticulo to set
	 */
	public void setTipoArticulo(TipoArticulo tipoArticulo) {
		if (articuloEnvasado!=null) return;
		pcs.firePropertyChange("tipoArticulo", this.tipoArticulo, this.tipoArticulo = tipoArticulo);
	}

	/**
	 * @return the familia
	 */
	public FamiliaArticulo getFamilia() {
		if (articuloEnvasado!=null) articuloEnvasado.getFamilia();
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia(FamiliaArticulo familia) {
		if (articuloEnvasado!=null) return;
		pcs.firePropertyChange("familia", this.familia, this.familia = familia);
	}

	
	/**
	 * @return the unidadMedida
	 */
	public UnidadMedida getUnidadMedida() {
		if (articuloEnvasado!=null) return articuloEnvasado.getUnidadMedida();
		return unidadMedida;
	}

	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(UnidadMedida unidadMedida) {
		if (articuloEnvasado!=null) return;
		pcs.firePropertyChange("unidadMedida", this.unidadMedida, this.unidadMedida = unidadMedida);
	}

	/**
	 * @return the tipoIva
	 */
	public TipoIva getTipoIva() {
		if (articuloEnvasado!=null) return articuloEnvasado.getTipoIva();
		return tipoIva;
	}

	/**
	 * @param tipoIva the tipoIva to set
	 */
	public void setTipoIva(TipoIva tipoIva) {
		if (articuloEnvasado!=null) return;
		pcs.firePropertyChange("tipoIva", this.tipoIva, this.tipoIva = tipoIva);
	}

	/**
	 * @return the pesoUnidad
	 */
	public double getPesoUnidad() {
		if (articuloEnvasado!=null) return (articuloEnvasado.getPesoUnidad()*cantidadEnvasada);
		return pesoUnidad;
	}

	/**
	 * @param pesoUnidad the pesoUnidad to set
	 */
	public void setPesoUnidad(double pesoUnidad) {
		if (articuloEnvasado!=null) return;
		pcs.firePropertyChange("pesoUnidad", this.pesoUnidad, this.pesoUnidad = pesoUnidad);
	}

	/**
	 * @return the parentArticulo
	 */
	public Articulo getArticuloEnvasado() {
		return articuloEnvasado;
	}

	/**
	 * @param parentArticulo the parentArticulo to set
	 */
	public void setArticuloEnvasado(Articulo articuloEnvasado) {
		pcs.firePropertyChange("articuloEnvasado", this.articuloEnvasado, this.articuloEnvasado = articuloEnvasado);
	}

	/**
	 * @return the cantidadEnvasada
	 */
	public double getCantidadEnvasada() {
		return cantidadEnvasada;
	}

	/**
	 * @param cantidadEnvasada the cantidadEnvasada to set
	 */
	public void setCantidadEnvasada(double cantidadEnvasada) {
		pcs.firePropertyChange("cantidadEnvasada", this.cantidadEnvasada, this.cantidadEnvasada = cantidadEnvasada);
	}

	/**
	 * @return the pesoEnvase
	 */
	public double getPesoEnvase() {
		return pesoEnvase;
	}

	/**
	 * @param pesoEnvase the pesoEnvase to set
	 */
	public void setPesoEnvase(double pesoEnvase) {
		pcs.firePropertyChange("pesoEnvase", this.pesoEnvase, this.pesoEnvase = pesoEnvase);
	}

	/**
	 * @return the precioEnvase
	 */
	public double getPrecioEnvase() {
		return precioEnvase;
	}

	/**
	 * @param precioEnvase the precioEnvase to set
	 */
	public void setPrecioEnvase(double precioEnvase) {
		pcs.firePropertyChange("precioEnvase", this.precioEnvase, this.precioEnvase = precioEnvase);
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
	 * @return the permiteStockNegativo
	 */
	public boolean isPermiteStockNegativo() {
		return permiteStockNegativo;
	}

	/**
	 * @param permiteStockNegativo the permiteStockNegativo to set
	 */
	public void setPermiteStockNegativo(boolean permiteStockNegativo) {
		pcs.firePropertyChange("permiteStockNegativo", this.permiteStockNegativo,
				this.permiteStockNegativo = permiteStockNegativo);
	}

	/**
	 * @return the stock
	 */
	public double getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(double stock) {
		pcs.firePropertyChange("stock", this.stock, this.stock = stock);
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
