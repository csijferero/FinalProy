package es.altair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.Categorias;

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
		} catch (Exception e) {

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

}
