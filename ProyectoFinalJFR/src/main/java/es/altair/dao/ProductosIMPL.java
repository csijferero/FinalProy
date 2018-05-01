package es.altair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.Productos;

public class ProductosIMPL implements ProductosDAO {

	@Override
	public List<Productos> listado(int idCategoria) {
		List<Productos> productos = new ArrayList<Productos>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			productos = session.createQuery("FROM Productos Where categorias.idcategorias=:id").setParameter("id", idCategoria)
					.list();
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

}
