package es.altair.dao;

import es.altair.bean.Usuarios;

public interface UsuariosDAO {

	Usuarios comprobarUsuario(String correo, String contrasena);

	int validarRegistro(String email, String nick);

	void insertar(String nick, String email, String contraseña, String nombre, String apellidos, String direccion,
			Double contacto, String dni, byte[] event);

	byte[] getfpImage(int idUsuario);

	int validarRegistro(String correo, String login, String correoOld, String loginOld);

	Usuarios obtenerUsuario(String login);

	void actualizar(Integer idUsuario, String nick, String email, String contraseña, String nombre, String apellidos, String direccion,
			Double contacto, String dni, byte[] event, Integer tipoUsu);
	
	void actualizarSinIMG(Integer idUsuario, String nick, String email, String contraseña, String nombre, String apellidos, String direccion,
			Double contacto, String dni, Integer tipoUsu);

}
