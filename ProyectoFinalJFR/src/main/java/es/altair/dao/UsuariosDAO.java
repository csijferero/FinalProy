package es.altair.dao;

import java.util.List;

import es.altair.bean.Usuarios;

public interface UsuariosDAO {

	Usuarios obtenerUsuarioById(int id);

	List<Usuarios> listado();

	Usuarios comprobarUsuario(String correo, String contrasena);

	int validarRegistro(String email, String nick);

	void insertar(String nick, String email, String contraseña, String nombre, String apellidos, String direccion,
			Double contacto, String dni, byte[] event, String uuid);

	byte[] getfpImage(int idUsuario);

	int validarRegistro(String correo, String login, String correoOld, String loginOld);

	void actualizar(Integer idUsuario, String nick, String email, String contraseña, String nombre, String apellidos,
			String direccion, Double contacto, String dni, byte[] event, Integer tipoUsu);

	void actualizarSinIMG(Integer idUsuario, String nick, String email, String contraseña, String nombre,
			String apellidos, String direccion, Double contacto, String dni, Integer tipoUsu);

	void actualizar(Integer idUsuario, String nick, String email, String nombre, String apellidos, String direccion,
			Double contacto, String dni, byte[] event, Integer tipoUsu);

	void actualizarSinIMG(Integer idUsuario, String nick, String email, String nombre, String apellidos,
			String direccion, Double contacto, String dni, Integer tipoUsu);

	int activarUsuario(String uuid);

	void borrar(int c);
	
	String recuperarContraseña(String email);
}
