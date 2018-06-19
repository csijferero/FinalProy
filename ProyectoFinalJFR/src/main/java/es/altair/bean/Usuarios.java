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
@Table(name = "usuarios")
public class Usuarios implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer idusuarios;
	public String nick;
	public String email;
	public String contraseña;
	public String nombre;
	public String apellidos;
	public String direccion;
	public Double contacto;
	public String dni;
	public byte[] image;
	public String uuid;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idtipousuarios")
	private TipoUsuarios tipoUsuarios;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Set<Compras> comprasUsuario;

	public Usuarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuarios(String nick, String email, String contraseña, String nombre, String apellidos, String direccion,
			Double contacto, String dni, byte[] image, String uuid, TipoUsuarios tipoUsuarios) {
		super();
		this.nick = nick;
		this.email = email;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.contacto = contacto;
		this.dni = dni;
		this.image = image;
		this.tipoUsuarios = tipoUsuarios;
		this.uuid = uuid;
	}

	public Integer getIdusuarios() {
		return idusuarios;
	}

	public void setIdusuarios(Integer idusuarios) {
		this.idusuarios = idusuarios;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getContacto() {
		return contacto;
	}

	public void setContacto(Double contacto) {
		this.contacto = contacto;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public TipoUsuarios getTipoUsuarios() {
		return tipoUsuarios;
	}

	public void setTipoUsuarios(TipoUsuarios tipoUsuarios) {
		this.tipoUsuarios = tipoUsuarios;
	}

	public Set<Compras> getComprasUsuario() {
		return comprasUsuario;
	}

	public void setComprasUsuario(Set<Compras> comprasUsuario) {
		this.comprasUsuario = comprasUsuario;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
