package es.altair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.FormaEnvio;
import es.altair.bean.FormaPago;

public class FormaEnvioIMPL implements FormaEnvioDAO {

	public List<FormaEnvio> listado() {
		List<FormaEnvio> formaEnvio = new ArrayList<FormaEnvio>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			formaEnvio = session.createQuery("FROM FormaEnvio").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return formaEnvio;
	}
	
	public FormaEnvio getFormaEnvioById(int id) {
		FormaEnvio formaEnvio = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			formaEnvio = (FormaEnvio) session.createQuery("FROM FormaEnvio where idformaenvio=:id").setParameter("id", id).uniqueResult();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return formaEnvio;
	}

	public byte[] getfpImage(int idformaenvio) {
		byte[] fpImage = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		fpImage = (byte[]) session.createQuery("Select image From FormaEnvio Where idformaenvio=:id")
				.setParameter("id", idformaenvio).uniqueResult();

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

			if ((FormaEnvio) sesion.createQuery("FROM FormaEnvio WHERE nombre=:n").setParameter("n", nombre)
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
			sesion.createSQLQuery("INSERT INTO formaenvio (nombre, image)" + "values (:n, :i)")
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

			sesion.createQuery("DELETE FROM FormaEnvio WHERE idformaenvio=:c").setParameter("c", c).executeUpdate();

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

			if ((FormaEnvio) sesion.createQuery("FROM FormaEnvio WHERE nombre=:n").setParameter("n", nombre)
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

	public void actualizar(Integer idFormaEnvio, String nombre, byte[] event) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			sesion.beginTransaction();
			sesion.createQuery("UPDATE FormaEnvio SET nombre=:n, image=:im WHERE idformaenvio=:idFe")
					.setParameter("n", nombre).setParameter("im", event).setParameter("idFe", idFormaEnvio)
					.executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {

		} finally {
			sesion.close();
			sf.close();
		}
	}

	public void actualizarSinIMG(Integer idFormaEnvio, String nombre) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session sesion = sf.openSession();

		try {
			sesion.beginTransaction();
			sesion.createQuery("UPDATE FormaEnvio SET nombre=:n WHERE idformaenvio=:idFe").setParameter("n", nombre)
					.setParameter("idFe", idFormaEnvio).executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {

		} finally {
			sesion.close();
			sf.close();
		}
	}

}
