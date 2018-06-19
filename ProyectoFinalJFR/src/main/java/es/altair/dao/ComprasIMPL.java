package es.altair.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.Categorias;
import es.altair.bean.Compras;
import es.altair.bean.Compras_Productos;
import es.altair.bean.FormaEnvio;
import es.altair.bean.FormaPago;

public class ComprasIMPL implements ComprasDAO {

	public void save(Compras_Productos cp) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			sesion.beginTransaction();
			sesion.save(cp);
			sesion.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sesion.close();
			sf.close();
		}
	}

	public List<Compras> list(int id) {
		List<Compras> compras = null;
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			compras = session.createSQLQuery("Select * FROM compras where idusuarios=:id").addEntity(Compras.class)
					.setParameter("id", id).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return compras;
	}

	public Compras getComprasByUuid(String uuid) {
		Compras compras = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			compras = (Compras) session.createQuery("FROM Compras where uuid=:uuid").setParameter("uuid", uuid)
					.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return compras;
	}

}
