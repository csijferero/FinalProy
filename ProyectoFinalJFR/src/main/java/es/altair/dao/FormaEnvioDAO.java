package es.altair.dao;

import java.util.List;

import es.altair.bean.FormaEnvio;

public interface FormaEnvioDAO {

	public List<FormaEnvio> listado();

	public byte[] getfpImage(int idformaenvio);

	void insertar(String nombre, byte[] event);

	int validarRegistro(String nombre);

	void borrar(int c);

	int validarRegistro(String nombre, String nombreOld);

	void actualizar(Integer idFormaEnvio, String nombre, byte[] event);

	void actualizarSinIMG(Integer idFormaEnvio, String nombre);
}
