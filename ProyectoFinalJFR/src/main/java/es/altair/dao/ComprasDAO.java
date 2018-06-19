package es.altair.dao;

import java.util.List;

import es.altair.bean.Compras;
import es.altair.bean.Compras_Productos;

public interface ComprasDAO {

	void save(Compras_Productos cp);

	List<Compras> list(int id);

	Compras getComprasByUuid(String uuid);

}
