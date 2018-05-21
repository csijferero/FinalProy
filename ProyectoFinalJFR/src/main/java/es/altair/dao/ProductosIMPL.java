package es.altair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.Categorias;
import es.altair.bean.Productos;

public class ProductosIMPL implements ProductosDAO {

	@Override
	public List<Productos> listado(int idCategoria) {
		List<Productos> productos = new ArrayList<Productos>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			productos = session.createQuery("FROM Productos Where categorias.idcategorias=:id")
					.setParameter("id", idCategoria).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return productos;
	}

	@Override
	public byte[] getfpImage(int idproductos) {
		byte[] prImage = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		prImage = (byte[]) session.createQuery("Select image From Productos Where idproductos=:id")
				.setParameter("id", idproductos).uniqueResult();

		session.close();
		sf.close();

		return prImage;
	}

	@Override
	public Productos obtener(int idproductos) {
		Productos aux = null;
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			aux = sesion.get(Productos.class, idproductos);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sesion.close();
			sf.close();
		}
		return aux;
	}

	@Override
	public void insertar(int idcategorias, String nombre, String marca, String modelo, double precio, int garantia,
			int ano, byte[] event, String uuid) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();
			sesion.createSQLQuery(
					"INSERT INTO productos (idcategorias, nombre, precio, marca, modelo, ano, garantia, image, uuid)"
							+ "values (:id, :n, :p, :ma, :mo, :a, :g, :i, :u)")
					.setParameter("id", idcategorias).setParameter("n", nombre).setParameter("p", precio)
					.setParameter("ma", marca).setParameter("mo", modelo).setParameter("a", ano)
					.setParameter("g", garantia).setParameter("i", event).setParameter("u", uuid).executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
	}

	@Override
	public int validarRegistro(String nombre) {
		int valido = 0;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			if ((Productos) sesion.createQuery("FROM Productos WHERE nombre=:n").setParameter("n", nombre)
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

	@Override
	public void borrar(int c) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();
		try {
			sesion.beginTransaction();

			sesion.createQuery("DELETE FROM Productos WHERE idproductos=:c").setParameter("c", c).executeUpdate();

			sesion.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sesion.close();
			sf.close();
		}
	}

}
