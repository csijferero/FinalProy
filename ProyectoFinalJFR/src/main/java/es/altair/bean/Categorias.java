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
@Table(name = "categorias")
public class Categorias implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcategorias;
	private String nombre;
	private byte[] image;

	@OneToMany(mappedBy = "categorias", cascade = CascadeType.ALL)
	private Set<Productos> productosCategorias;

	public Categorias() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categorias(String nombre, byte[] image) {
		super();
		this.nombre = nombre;
		this.image = image;
	}

	public int getIdcategorias() {
		return idcategorias;
	}

	public void setIdcategorias(int idcategorias) {
		this.idcategorias = idcategorias;
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

	public Set<Productos> getProductosCategorias() {
		return productosCategorias;
	}

	public void setProductosCategorias(Set<Productos> productosCategorias) {
		this.productosCategorias = productosCategorias;
	}

}
