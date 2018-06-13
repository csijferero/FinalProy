package es.altair.dao;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import es.altair.bean.TipoUsuarios;
import es.altair.bean.Usuarios;

public class UsuariosIMPL implements UsuariosDAO {

	private String key = "Libros123$%";

	public Usuarios comprobarUsuario(String login, String password) {
		Usuarios usu = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();
			usu = (Usuarios) sesion.createQuery("FROM Usuarios WHERE email=:e AND contraseña=AES_ENCRYPT(:p, :pass)")
					.setParameter("e", login).setParameter("pass", key).setParameter("p", password).uniqueResult();

			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}

		return usu;
	}

	public int activarUsuario(String uuid) {
		int valido = 0;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();
			if ((Usuarios) sesion.createQuery("FROM Usuarios WHERE uuid=:u").setParameter("u", uuid)
					.uniqueResult() == null) {
				valido = 0;
			} else {
				if ((Usuarios) sesion.createQuery("FROM Usuarios WHERE uuid=:u AND idtipousuarios != 3")
						.setParameter("u", uuid).uniqueResult() != null) {
					valido = 1;
				} else {
					sesion.createQuery("UPDATE Usuarios SET idtipousuarios=1 WHERE uuid=:u").setParameter("u", uuid).executeUpdate();
					valido = 2;
				}
			}
			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
		return valido;
	}

	@Override
	public byte[] getfpImage(int idUsuario) {
		byte[] fpImage = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		fpImage = (byte[]) session.createQuery("Select image From Usuarios Where idusuarios=:id")
				.setParameter("id", idUsuario).uniqueResult();

		session.close();
		sf.close();

		return fpImage;
	}

	public int validarRegistro(String email, String nick) {
		int valido = 0;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			if ((Usuarios) sesion.createQuery("FROM Usuarios WHERE email=:e").setParameter("e", email)
					.uniqueResult() == null) {
				valido = 0;
				if ((Usuarios) sesion.createQuery("FROM Usuarios WHERE nick=:n").setParameter("n", nick)
						.uniqueResult() == null)
					valido = 0;
				else
					valido = 2;
			} else
				valido = 1;

			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
		return valido;
	}

	public int validarRegistro(String correo, String login, String correoOld, String loginOld) {
		int valido = 0;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			if ((Usuarios) sesion.createQuery("FROM Usuarios WHERE email=:c").setParameter("c", correo)
					.uniqueResult() == null || correo.equals(correoOld)) {
				valido = 0;
				if ((Usuarios) sesion.createQuery("FROM Usuarios WHERE nick=:l").setParameter("l", login)
						.uniqueResult() == null || login.equals(loginOld))
					valido = 0;
				else
					valido = 2;
			} else
				valido = 1;

			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
		return valido;
	}

	public void insertar(String nick, String email, String contraseña, String nombre, String apellidos,
			String direccion, Double contacto, String dni, byte[] event, String uuid) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();
			sesion.createSQLQuery(
					"INSERT INTO usuarios (idtipousuarios, nick, email, contraseña, nombre, apellidos, direccion, contacto, dni, image, uuid)"
							+ "values (3, :n, :e, AES_ENCRYPT(:p, :pass), :no, :a, :d, :c, :dni, :i, :u)")
					.setParameter("n", nick).setParameter("e", email).setParameter("no", nombre)
					.setParameter("p", contraseña).setParameter("d", direccion).setParameter("c", contacto)
					.setParameter("dni", dni).setParameter("pass", key).setParameter("a", apellidos)
					.setParameter("i", event).setParameter("u", uuid).executeUpdate();

			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
	}

	public Usuarios obtenerUsuario(String login) {
		Usuarios usu = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			usu = (Usuarios) sesion.createQuery("FROM Usuarios WHERE nick=:l").setParameter("l", login).uniqueResult();

			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
		return usu;
	}

	public void actualizar(Integer idUsuario, String nick, String email, String contraseña, String nombre,
			String apellidos, String direccion, Double contacto, String dni, byte[] event, Integer tipoUsu) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			sesion.beginTransaction();
			sesion.createQuery(
					"UPDATE Usuarios SET nombre=:n, apellidos=:a, email=:c, nick=:l, contraseña=AES_ENCRYPT(:p, :pass), idtipousuarios=:tu, direccion=:dir, contacto=:con, dni=:dni, image=:im WHERE idusuarios=:idUsu")
					.setParameter("n", nombre).setParameter("c", email).setParameter("l", nick)
					.setParameter("p", contraseña).setParameter("pass", key).setParameter("idUsu", idUsuario)
					.setParameter("tu", tipoUsu).setParameter("a", apellidos).setParameter("con", contacto)
					.setParameter("dir", direccion).setParameter("dni", dni).setParameter("im", event).executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {

		} finally {
			sesion.close();
			sf.close();
		}
	}

	public void actualizarSinIMG(Integer idUsuario, String nick, String email, String contraseña, String nombre,
			String apellidos, String direccion, Double contacto, String dni, Integer tipoUsu) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			sesion.beginTransaction();
			sesion.createQuery(
					"UPDATE Usuarios SET nombre=:n, apellidos=:a, email=:c, nick=:l, contraseña=AES_ENCRYPT(:p, :pass), idtipousuarios=:tu, direccion=:dir, contacto=:con, dni=:dni WHERE idusuarios=:idUsu")
					.setParameter("n", nombre).setParameter("c", email).setParameter("l", nick)
					.setParameter("p", contraseña).setParameter("pass", key).setParameter("idUsu", idUsuario)
					.setParameter("tu", tipoUsu).setParameter("a", apellidos).setParameter("con", contacto)
					.setParameter("dir", direccion).setParameter("dni", dni).executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {

		} finally {
			sesion.close();
			sf.close();
		}
	}

}
