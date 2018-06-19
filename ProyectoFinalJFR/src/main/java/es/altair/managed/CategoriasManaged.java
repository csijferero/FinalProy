package es.altair.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import es.altair.bean.Categorias;
import es.altair.bean.FormaPago;
import es.altair.dao.CategoriasDAO;
import es.altair.dao.CategoriasIMPL;

@ManagedBean
public class CategoriasManaged implements Serializable {

	CategoriasDAO catDAO = new CategoriasIMPL();

	private Integer idCategoria;
	private String nombreOld;
	private String nombre;
	private UploadedFile file;

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreOld() {
		return nombreOld;
	}

	public void setNombreOld(String nombreOld) {
		this.nombreOld = nombreOld;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	private List<Categorias> categorias = new ArrayList<Categorias>();

	public List<Categorias> getCategorias() {
		categorias = catDAO.listado();
		return categorias;
	}

	public void setCategorias(List<Categorias> categorias) {
		this.categorias = categorias;
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		CategoriasDAO catDAO = new CategoriasIMPL();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.
			String id = context.getExternalContext().getRequestParameterMap().get("imageID");
			byte[] image = catDAO.getfpImage(Integer.valueOf(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(image));
		}
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	private StreamedContent image;

	public String registrarCategoria() {
		FacesMessage message = null;

		String redirect;
		int respuesta = catDAO.validarRegistro(nombre);

		if (file.getFileName().equals("")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imagen Obligatoria", "Imagen Requerida");
			redirect = "categorias?faces-redirect=true";
		} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
				&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido", "Imagen Invalida");
			redirect = "categorias?faces-redirect=true";
		} else if (respuesta == 0) {
			catDAO.insertar(nombre, file.getContents());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria Registrada", "Categoria Registrada");
			redirect = "categorias?faces-redirect=true";
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este nombre ya existe. Pruebe con otro",
					"Nombre ya registrado");
			redirect = "categorias?faces-redirect=true";
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}

	public String borrarCategoria(int c) {
		FacesMessage message = null;

		String redirect;

		catDAO.borrar(c);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria Borrada", "Categoria Borrada");
		redirect = "categorias?faces-redirect=true";

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}

	public String editarCategoria(int id) {
		FacesMessage message = null;

		String redirect = "";

		int respuesta = catDAO.validarRegistro(nombre, nombreOld); // Comprobamos si es valido

		if (respuesta == 0) { // Es valido
			if (file.getFileName().equals("")) {
				catDAO.actualizarSinIMG(idCategoria, nombre);
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria Actualizada correctamente",
						"Usuario Actualizado correctamente");
				redirect = "categorias?faces-redirect=true";
			} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
					&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido",
						"Imagen Invalida");
				redirect = "categorias?faces-redirect=true";
			} else {
				catDAO.actualizar(idCategoria, nombre, file.getContents());
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria Actualizada correctamente",
						"Usuario Actualizado correctamente");
				redirect = "categorias?faces-redirect=true";
				// Poner en sesion
			}
		} else if (respuesta == 1) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya registrado. Pruebe con otro",
					"Email ya registrado");
			redirect = "categorias?faces-redirect=true";
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return redirect;
	}

	public void cargaEdit(Categorias cat) {
		idCategoria = cat.getIdcategorias();
		nombre = cat.getNombre();
		nombreOld = cat.getNombre();
	}

}
