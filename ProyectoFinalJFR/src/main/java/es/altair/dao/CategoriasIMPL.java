package es.altair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.Categorias;
import es.altair.bean.FormaPago;

public class CategoriasIMPL implements CategoriasDAO {

	@Override
	public List<Categorias> listado() {
		List<Categorias> categorias = new ArrayList<Categorias>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			categorias = session.createQuery("FROM Categorias").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return categorias;
	}

	@Override
	public byte[] getfpImage(int idCategoria) {
		byte[] fpImage = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		fpImage = (byte[]) session.createQuery("Select image From Categorias Where idcategorias=:id")
				.setParameter("id", idCategoria).uniqueResult();

		session.close();
		sf.close();

		return fpImage;
	}

	public int validarRegistro(String nombre) {
		int valido = 0;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			if ((Categorias) sesion.createQuery("FROM Categorias WHERE nombre=:n").setParameter("n", nombre)
					.uniqueResult() == null) {
				valido = 0;
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

	public void insertar(String nombre, byte[] event) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();
			sesion.createSQLQuery("INSERT INTO categorias (nombre, image)" + "values (:n, :i)")
					.setParameter("n", nombre).setParameter("i", event).executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
	}

	public void borrar(int c) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			sesion.createQuery("DELETE FROM Categorias WHERE idcategorias=:c").setParameter("c", c).executeUpdate();

			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
	}

	public int validarRegistro(String nombre, String nombreOld) {
		int valido = 0;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			if ((Categorias) sesion.createQuery("FROM Categorias WHERE nombre=:n").setParameter("n", nombre)
					.uniqueResult() == null || nombre.equals(nombreOld)) {
				valido = 0;
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

	public void actualizar(Integer idCategoria, String nombre, byte[] event) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			sesion.beginTransaction();
			sesion.createQuery("UPDATE Categorias SET nombre=:n, image=:im WHERE idcategorias=:idCat")
					.setParameter("n", nombre).setParameter("im", event).setParameter("idCat", idCategoria)
					.executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {

		} finally {
			sesion.close();
			sf.close();
		}
	}

	public void actualizarSinIMG(Integer idCategoria, String nombre) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			sesion.beginTransaction();
			sesion.createQuery("UPDATE Categorias SET nombre=:n WHERE idcategorias=:idCat").setParameter("n", nombre)
					.setParameter("idCat", idCategoria).executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {

		} finally {
			sesion.close();
			sf.close();
		}
	}

}
