package es.altair.dao;

import java.util.List;

import es.altair.bean.TipoUsuarios;

public interface TipoUsuarioDAO {

	TipoUsuarios obtener(int id);

	List<TipoUsuarios> listado();

}
