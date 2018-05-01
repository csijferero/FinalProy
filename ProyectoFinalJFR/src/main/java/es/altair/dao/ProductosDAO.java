package es.altair.dao;

import java.util.List;

import es.altair.bean.Productos;

public interface ProductosDAO {

	public List<Productos> listado(int idCategoria);

	public byte[] getfpImage(int idproductos);
	
	public Productos obtener(int idproductos);

}
