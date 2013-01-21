package com.veisite.vegecom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class PrecioArticuloCliente extends ModelObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne @NotNull
    private Cliente cliente;

    @ManyToOne @NotNull
    private Articulo articulo;
    
    @Column
    private double precio;
    
    /**
     * Procentaje de descuento sobre tarifa general del artículo
     *  Se utiliza para obviar precio anterior y calcularlo sobre el
     *  precio de tarifa. Si null, el precio será el anterior.
     */
    @Column 
    private double descuento;

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
	 * @return the proveedor
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setCliente(Cliente cliente) {
		pcs.firePropertyChange("cliente", this.cliente,
				this.cliente = cliente);
	}

	/**
	 * @return the articulo
	 */
	public Articulo getArticulo() {
		return articulo;
	}

	/**
	 * @param articulo the articulo to set
	 */
	public void setArticulo(Articulo articulo) {
		pcs.firePropertyChange("articulo", this.articulo, this.articulo = articulo);
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		pcs.firePropertyChange("precio", this.precio, this.precio = precio);
	}

	/**
	 * @return the descuento
	 */
	public double getDescuento() {
		return descuento;
	}

	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(double descuento) {
		pcs.firePropertyChange("descuento", this.descuento,
				this.descuento = descuento);
	}

    
}
