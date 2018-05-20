package es.altair.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

}
