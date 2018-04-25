package es.altair.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "formapago")
public class FormaPago implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idformapago;
	private String nombre;
	private byte[] image;

	@OneToMany(mappedBy = "formaPago", cascade = CascadeType.ALL)
	private Set<Compras> comprasPago;

	public FormaPago() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormaPago(String nombre, byte[] image) {
		super();
		this.nombre = nombre;
		this.image = image;
	}

	public int getIdformapago() {
		return idformapago;
	}

	public void setIdformapago(int idformapago) {
		this.idformapago = idformapago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Set<Compras> getComprasPago() {
		return comprasPago;
	}

	public void setComprasPago(Set<Compras> comprasPago) {
		this.comprasPago = comprasPago;
	}

}
