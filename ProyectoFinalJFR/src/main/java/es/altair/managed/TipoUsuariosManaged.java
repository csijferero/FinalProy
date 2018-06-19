package es.altair.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import es.altair.bean.Productos;
import es.altair.bean.TipoUsuarios;
import es.altair.dao.FormaPagoDAO;
import es.altair.dao.FormaPagoIMPL;
import es.altair.dao.TipoUsuarioDAO;
import es.altair.dao.TipoUsuarioIMPL;

@ManagedBean
public class TipoUsuariosManaged implements Serializable {
	
	TipoUsuarioDAO tuDAO = new TipoUsuarioIMPL();
	
	private int idtipousuarios;
	private String nombre;
	private List<TipoUsuarios> tiposUsuarios = new ArrayList<TipoUsuarios>();
	
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
	public List<TipoUsuarios> getTiposUsuarios() {
		tiposUsuarios = tuDAO.listado();
		return tiposUsuarios;
	}
	public void setTiposUsuarios(List<TipoUsuarios> tiposUsuarios) {
		this.tiposUsuarios = tiposUsuarios;
	}
	
	


}
