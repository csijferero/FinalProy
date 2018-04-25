package es.altair.dao;

import java.util.List;

import es.altair.bean.Categorias;

public interface CategoriasDAO {
	
	public List<Categorias> listado();
	public byte[] getfpImage(int idCategoria);

}
