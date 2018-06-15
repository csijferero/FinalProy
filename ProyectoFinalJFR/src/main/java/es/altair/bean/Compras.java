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
	private String uuid;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idformaenvio")
	private FormaEnvio formaEnvio;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idformapago")
	private FormaPago formaPago;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idusuarios")
	private Usuarios usuario;

	@OneToMany(mappedBy = "compra")
	private Set<Compras_Productos> comprasProductos;

	public Compras() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Compras(Date fecha, String uuid, FormaEnvio formaEnvio, FormaPago formaPago, Usuarios usuario) {
		super();
		this.fecha = fecha;
		this.uuid = uuid;
		this.formaEnvio = formaEnvio;
		this.formaPago = formaPago;
		this.usuario = usuario;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public FormaEnvio getFormaEnvio() {
		return formaEnvio;
	}

	public void setFormaEnvio(FormaEnvio formaEnvio) {
		this.formaEnvio = formaEnvio;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Set<Compras_Productos> getComprasProductos() {
		return comprasProductos;
	}

	public void setComprasProductos(Set<Compras_Productos> comprasProductos) {
		this.comprasProductos = comprasProductos;
	}
	
}
