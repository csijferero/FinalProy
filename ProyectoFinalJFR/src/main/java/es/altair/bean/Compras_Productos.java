package es.altair.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "compras_productos")
public class Compras_Productos implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcompras_productos;

	private int cantidad;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idcompras")
	private Compras compra;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idproductos")
	private Productos producto;

	public Compras_Productos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Compras_Productos(int cantidad, Compras compra, Productos producto) {
		super();
		this.cantidad = cantidad;
		this.compra = compra;
		this.producto = producto;
	}

	public int getIdcompras_productos() {
		return idcompras_productos;
	}

	public void setIdcompras_productos(int idcompras_productos) {
		this.idcompras_productos = idcompras_productos;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Compras getCompra() {
		return compra;
	}

	public void setCompra(Compras compra) {
		this.compra = compra;
	}

	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

}
