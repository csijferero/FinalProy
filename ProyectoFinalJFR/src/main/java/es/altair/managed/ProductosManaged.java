package es.altair.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.altair.bean.Productos;
import es.altair.dao.ProductosDAO;
import es.altair.dao.ProductosIMPL;

@ManagedBean
@SessionScoped
public class ProductosManaged implements Serializable {

	ProductosDAO proDAO = new ProductosIMPL();

	private Productos producto = new Productos();

	public Productos getProducto() {
		producto = proDAO.obtener(Integer.parseInt(productoId));
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	private List<Productos> productos = new ArrayList<Productos>();

	public List<Productos> getProductos() {
		productos = proDAO.listado(Integer.parseInt(categoriaId));
		return productos;
	}

	public void setProductos(List<Productos> productos) {
		this.productos = productos;
	}

	private String productoId;

	public String getProductoId() {
		return productoId;
	}

	public void setProductoId(String productoId) {
		this.productoId = productoId;
	}

	private String categoriaId;

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		ProductosDAO proDAO = new ProductosIMPL();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.
			String id = context.getExternalContext().getRequestParameterMap().get("imageID");
			byte[] image = proDAO.getfpImage(Integer.valueOf(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(image));
		}
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	private StreamedContent image;

}
