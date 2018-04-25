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
@Table(name = "tipousuarios")
public class TipoUsuarios implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idtipousuarios;
	private String nombre;

	@OneToMany(mappedBy = "tipoUsuarios", cascade = CascadeType.ALL)
	private Set<Usuarios> usuariosTipo;

	public TipoUsuarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipoUsuarios(String nombre) {
		super();
		this.nombre = nombre;
	}

	public int getIdtipousuarios() {
		return idtipousuarios;
	}

	public void setIdtipousuarios(int idtipousuarios) {
		this.idtipousuarios = idtipousuarios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Usuarios> getUsuariosTipo() {
		return usuariosTipo;
	}

	public void setUsuariosTipo(Set<Usuarios> usuariosTipo) {
		this.usuariosTipo = usuariosTipo;
	}

}
