package es.altair.dao;

import java.util.List;

import es.altair.bean.FormaPago;

public interface FormaPagoDAO {

	List<FormaPago> listado();

	byte[] getfpImage(int idformapago);

	void insertar(String nombre, byte[] event);

	int validarRegistro(String nombre);

	void borrar(int c);

	int validarRegistro(String nombre, String nombreOld);

	void actualizarSinIMG(Integer idFormaPago, String nombre);

	void actualizar(Integer idFormaPago, String nombre, byte[] event);
}
