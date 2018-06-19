package es.altair.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import es.altair.bean.FormaEnvio;
import es.altair.bean.FormaPago;
import es.altair.dao.FormaEnvioDAO;
import es.altair.dao.FormaEnvioIMPL;
import es.altair.dao.FormaPagoDAO;
import es.altair.dao.FormaPagoIMPL;

@ManagedBean
public class FormaEnvioManaged implements Serializable {

	private Integer idFormaEnvio;
	private String nombreOld;
	private String nombre;
	private UploadedFile file;
	private StreamedContent image;
	private List<FormaEnvio> formasDeEnvio = new ArrayList<FormaEnvio>();
	FormaEnvioDAO feDAO = new FormaEnvioIMPL();

	public Integer getIdFormaEnvio() {
		return idFormaEnvio;
	}

	public void setIdFormaEnvio(Integer idFormaEnvio) {
		this.idFormaEnvio = idFormaEnvio;
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

	public List<FormaEnvio> getFormasDeEnvio() {
		formasDeEnvio = feDAO.listado();
		return formasDeEnvio;
	}

	public void setFormasDeEnvio(List<FormaEnvio> formasDeEnvio) {
		this.formasDeEnvio = formasDeEnvio;
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.
			String id = context.getExternalContext().getRequestParameterMap().get("imageID");
			byte[] image = feDAO.getfpImage(Integer.valueOf(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(image));
		}
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public String registrarFormaEnvio() {
		FacesMessage message = null;

		String redirect;
		int respuesta = feDAO.validarRegistro(nombre);

		if (file.getFileName().equals("")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imagen Obligatoria", "Imagen Requerida");
			redirect = "inicio?faces-redirect=true";
		} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
				&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido", "Imagen Invalida");
			redirect = "inicio?faces-redirect=true";
		} else if (respuesta == 0) {
			feDAO.insertar(nombre, file.getContents());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Forma de envio Registrada",
					"Forma de envio Registrada");
			redirect = "inicio?faces-redirect=true";
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este nombre ya existe. Pruebe con otro",
					"Nombre ya registrado");
			redirect = "inicio?faces-redirect=true";
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}

	public String borrarFormaEnvio(int c) {
		FacesMessage message = null;

		String redirect;

		feDAO.borrar(c);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Forma de Envio Borrada", "Forma de Envio Borrada");
		redirect = "inicio?faces-redirect=true";

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}

	public String editarFormaPago(int id) {
		FacesMessage message = null;

		String redirect = "";

		int respuesta = feDAO.validarRegistro(nombre, nombreOld); // Comprobamos si es valido

		if (respuesta == 0) { // Es valido
			if (file.getFileName().equals("")) {
				feDAO.actualizarSinIMG(idFormaEnvio, nombre);
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Envio Actualizado correctamente",
						"Usuario Actualizado correctamente");
				redirect = "inicio?faces-redirect=true";
			} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
					&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido",
						"Imagen Invalida");
				redirect = "inicio?faces-redirect=true";
			} else {
				feDAO.actualizar(idFormaEnvio, nombre, file.getContents());
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Envio Actualizado correctamente",
						"Usuario Actualizado correctamente");
				redirect = "inicio?faces-redirect=true";
				// Poner en sesion
			}
		} else if (respuesta == 1) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya registrado. Pruebe con otro",
					"Email ya registrado");
			redirect = "inicio?faces-redirect=true";
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return redirect;
	}

	public void cargaEdit(FormaEnvio fe) {
		idFormaEnvio = fe.getIdformaenvio();
		nombre = fe.getNombre();
		nombreOld = fe.getNombre();
	}

}
