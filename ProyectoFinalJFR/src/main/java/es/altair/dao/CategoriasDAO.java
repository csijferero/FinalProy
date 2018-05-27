package es.altair.dao;

import java.util.List;

import es.altair.bean.Categorias;

public interface CategoriasDAO {
	
	public List<Categorias> listado();
	public byte[] getfpImage(int idCategoria);
	void insertar(String nombre, byte[] event);
	int validarRegistro(String nombre);
	void borrar(int c);
	void actualizarSinIMG(Integer idCategoria, String nombre);
	void actualizar(Integer idCategoria, String nombre, byte[] event);
	int validarRegistro(String nombre, String nombreOld);
}
