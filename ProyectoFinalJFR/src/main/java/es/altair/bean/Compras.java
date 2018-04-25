package es.altair.bean;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "compras")
public class Compras implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcompras;

	private Date fecha;
	private String nombre;
	private String apellidos;
	private Double contacto;
	private String direccion;
	private String dni;
	private String uuid;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idformaenvio")
	private FormaPago formaEnvio;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idformapago")
	private FormaPago formaPago;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idusuarios")
	private FormaPago usuario;

	@OneToMany(mappedBy = "compra")
	private Set<Compras_Productos> comprasProductos;

	public Compras() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Compras(Date fecha, String nombre, String apellidos, Double contacto, String direccion, String dni,
			String uuid, FormaPago formaEnvio, FormaPago formaPago, FormaPago usuario) {
		super();
		this.fecha = fecha;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contacto = contacto;
		this.direccion = direccion;
		this.dni = dni;
		this.uuid = uuid;
		this.formaEnvio = formaEnvio;
		this.formaPago = formaPago;
		this.usuario = usuario;
	}

	public Compras(Date fecha, String nombre, String apellidos, Double contacto, String direccion, String dni,
			String uuid, FormaPago formaEnvio, FormaPago formaPago) {
		super();
		this.fecha = fecha;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contacto = contacto;
		this.direccion = direccion;
		this.dni = dni;
		this.uuid = uuid;
		this.formaEnvio = formaEnvio;
		this.formaPago = formaPago;
	}

	public int getIdcompras() {
		return idcompras;
	}

	public void setIdcompras(int idcompras) {
		this.idcompras = idcompras;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Double getContacto() {
		return contacto;
	}

	public void setContacto(Double contacto) {
		this.contacto = contacto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public FormaPago getFormaEnvio() {
		return formaEnvio;
	}

	public void setFormaEnvio(FormaPago formaEnvio) {
		this.formaEnvio = formaEnvio;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public FormaPago getUsuario() {
		return usuario;
	}

	public void setUsuario(FormaPago usuario) {
		this.usuario = usuario;
	}

	public Set<Compras_Productos> getComprasProductos() {
		return comprasProductos;
	}

	public void setComprasProductos(Set<Compras_Productos> comprasProductos) {
		this.comprasProductos = comprasProductos;
	}

}
