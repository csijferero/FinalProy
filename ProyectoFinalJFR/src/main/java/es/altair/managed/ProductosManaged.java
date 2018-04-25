package es.altair.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import es.altair.bean.Productos;

@ManagedBean
@SessionScoped
public class ProductosManaged implements Serializable {

	private List<Productos> productos = new ArrayList<Productos>();

	public List<Productos> getProductos() {
		return productos;
	}

	public void setProductos(List<Productos> productos) {
		this.productos = productos;
	}

	private String CategoriaId;

	public String getCategoriaId() {
		return CategoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		CategoriaId = categoriaId;
	}

}
