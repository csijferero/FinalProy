package es.altair.dao;

import java.util.List;

import es.altair.bean.FormaPago;

public interface FormaPagoDAO {

	public List<FormaPago> listado();
	public byte[] getfpImage(int idformapago);
}
