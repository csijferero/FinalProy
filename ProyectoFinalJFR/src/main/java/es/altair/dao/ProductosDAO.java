package es.altair.dao;

import java.util.List;

import es.altair.bean.Productos;

public interface ProductosDAO {

	public List<Productos> listado(int idCategoria);
	public byte[] getfpImage(int idproductos);
	public Productos obtener(int idproductos);
	void insertar(int idcategorias, String nombre, String marca, String modelo, double precio, int garantia, int ano, byte[] event, String uuid);
	int validarRegistro(String nombre);
	void borrar(int c);
}
