package es.altair.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Productos implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idproductos;

	private String nombre;
	private Double precio;
	private String marca;
	private String modelo;
	private int ano;
	private int garantia;
	private byte[] image;
	private String uuid;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idcategorias")
	private Categorias categorias;

	@OneToMany(mappedBy = "producto")
	private Set<Compras_Productos> comprasProductos;

	public Productos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Productos(String nombre, Double precio, String marca, String modelo, int ano, int garantia, byte[] image,
			String uuid, Categorias categorias) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.garantia = garantia;
		this.image = image;
		this.uuid = uuid;
		this.categorias = categorias;
	}

	public int getIdproductos() {
		return idproductos;
	}

	public void setIdproductos(int idproductos) {
		this.idproductos = idproductos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getGarantia() {
		return garantia;
	}

	public void setGarantia(int garantia) {
		this.garantia = garantia;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Categorias getCategorias() {
		return categorias;
	}

	public void setCategorias(Categorias categorias) {
		this.categorias = categorias;
	}

	public Set<Compras_Productos> getComprasProductos() {
		return comprasProductos;
	}

	public void setComprasProductos(Set<Compras_Productos> comprasProductos) {
		this.comprasProductos = comprasProductos;
	}

}
