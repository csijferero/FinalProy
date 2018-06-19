package es.altair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.FormaPago;
import es.altair.bean.TipoUsuarios;

public class TipoUsuarioIMPL implements TipoUsuarioDAO {

	public TipoUsuarios obtener(int id) {
		TipoUsuarios aux = null;
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			aux = sesion.get(TipoUsuarios.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sesion.close();
			sf.close();
		}
		return aux;
	}

	public List<TipoUsuarios> listado() {
		List<TipoUsuarios> tipoUsuarios = new ArrayList<TipoUsuarios>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			tipoUsuarios = session.createQuery("FROM TipoUsuarios").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return tipoUsuarios;
	}

}
