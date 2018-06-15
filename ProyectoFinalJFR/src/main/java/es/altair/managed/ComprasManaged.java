package es.altair.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import es.altair.bean.Compras;
import es.altair.bean.Compras_Productos;
import es.altair.bean.Productos;
import es.altair.bean.Usuarios;
import es.altair.dao.ComprasDAO;
import es.altair.dao.ComprasIMPL;
import es.altair.dao.FormaEnvioDAO;
import es.altair.dao.FormaEnvioIMPL;
import es.altair.dao.FormaPagoDAO;
import es.altair.dao.FormaPagoIMPL;
import es.altair.dao.ProductosDAO;
import es.altair.dao.ProductosIMPL;

@ManagedBean
@ApplicationScoped
public class ComprasManaged implements Serializable {

	@ManagedProperty(value = "#{productosManaged}")
	ProductosManaged productosManaged;

	public ProductosManaged getProductosManaged() {
		return productosManaged;
	}

	public void setProductosManaged(ProductosManaged productosManaged) {
		this.productosManaged = productosManaged;
	}

	ProductosDAO proDAO = new ProductosIMPL();
	FormaEnvioDAO feDAO = new FormaEnvioIMPL();
	FormaPagoDAO fpDAO = new FormaPagoIMPL();
	ComprasDAO cpDAO = new ComprasIMPL();

	private List<Productos> carrito = new ArrayList<Productos>();
	private Integer idFormaPago;
	private Integer idFormaEnvio;

	public Integer getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	public Integer getIdFormaEnvio() {
		return idFormaEnvio;
	}

	public void setIdFormaEnvio(Integer idFormaEnvio) {
		this.idFormaEnvio = idFormaEnvio;
	}

	public List<Productos> getCarrito() {
		return carrito;
	}

	public void setCarrito(List<Productos> carrito) {
		this.carrito = carrito;
	}

	public List<Productos> productosCheckOut() { // Lista con productos no repetidos
		List<Productos> auxiliar = new ArrayList<Productos>();

		for (Productos producto : carrito) {
			if (!auxiliar.contains(producto))
				auxiliar.add(producto);
		}

		return auxiliar;
	}

	public int cantidadProductosCheckOut(Productos pro) {
		int cantidad = 0;

		for (Productos producto : carrito) {
			if (producto.equals(pro))
				cantidad++;
		}

		return cantidad;
	}

	public String crearCompra() {
		FacesMessage message = null;
		String destino = "";

		if (carrito.size() == 0) {
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Carrito Vacio, añada productos para realizar compras", "Compra realizada con Exito");
			destino= "checkout";
		} else {

			Compras compra = new Compras(new Date(), UUID.randomUUID().toString(),
					feDAO.getFormaEnvioById(idFormaEnvio), fpDAO.getFormaPagoById(idFormaPago),
					((Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")));

			for (Productos item : productosCheckOut()) {
				Compras_Productos registro = new Compras_Productos(cantidadProductosCheckOut(item), compra, item);
				cpDAO.save(registro);
			}

			deleteAllCarrito();
			setIdFormaEnvio(null);
			setIdFormaPago(null);

			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra realizada con Exito",
					"Compra realizada con Exito");
			destino = "inicio?faces-redirect=true";
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return destino;
	}

	public void anadirCarrito(Productos pro, int cantidad) {
		FacesMessage message = null;

		for (int i = 0; i < cantidad; i++) {
			carrito.add(pro);
		}

		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Añadido correctamente",
				"Producto Añadido correctamente");

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		productosManaged.clear();
	}

	public void deleteCarrito(Productos pro) {
		FacesMessage message = null;

		while (carrito.contains(pro)) {
			carrito.remove(pro);
		}

		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Borrado correctamente",
				"Producto Borrado correctamente");

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}

	public void deleteAllCarrito() {
		FacesMessage message = null;

		carrito.clear();

		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Carrito Vaciado correctamente",
				"Carrito Vaciado correctamente");

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}

	public void deletePro(Productos pro) {
		FacesMessage message = null;

		carrito.remove(pro);

		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Borrado correctamente",
				"Producto Borrado correctamente");

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}

	public int totalCarrito() {
		int total = 0;

		for (Productos producto : carrito) {
			total += producto.getPrecio();
		}
		return total;
	}

}
