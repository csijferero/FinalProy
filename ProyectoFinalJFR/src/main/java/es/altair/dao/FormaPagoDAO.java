package es.altair.dao;

import java.util.List;

import es.altair.bean.FormaPago;

public interface FormaPagoDAO {

	public List<FormaPago> listado();
	public byte[] getfpImage(int idformapago);
	void insertar(String nombre, byte[] event);
	int validarRegistro(String nombre);
	public void borrar(int c);
}
