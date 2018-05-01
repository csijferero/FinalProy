package es.altair.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.bean.FormaPago;

public class FormaPagoIMPL implements FormaPagoDAO {

	public List<FormaPago> listado() {
		List<FormaPago> formaPago = new ArrayList<FormaPago>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			session.beginTransaction();
			formaPago = session.createQuery("FROM FormaPago").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return formaPago;
	}

	public byte[] getfpImage(int idformapago) {
		byte[] fpImage = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		fpImage = (byte[]) session.createQuery("Select image From FormaPago Where idformapago=:id")
				.setParameter("id", idformapago).uniqueResult();

		session.close();
		sf.close();

		return fpImage;
	}

}
