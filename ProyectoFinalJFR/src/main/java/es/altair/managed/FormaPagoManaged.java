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

import es.altair.bean.FormaPago;
import es.altair.dao.FormaPagoDAO;
import es.altair.dao.FormaPagoIMPL;

@ManagedBean
public class FormaPagoManaged implements Serializable {

	private String nombre;
	private UploadedFile file;

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

	FormaPagoDAO fpDAO = new FormaPagoIMPL();

	private List<FormaPago> formasDePago = new ArrayList<FormaPago>();

	public List<FormaPago> getFormasDePago() {
		formasDePago = fpDAO.listado();
		return formasDePago;
	}

	public void setFormasDePago(List<FormaPago> formasDePago) {
		this.formasDePago = formasDePago;
	}

	private StreamedContent image;

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		FormaPagoDAO fpDAO = new FormaPagoIMPL();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.
			String id = context.getExternalContext().getRequestParameterMap().get("imageID");
			byte[] image = fpDAO.getfpImage(Integer.valueOf(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(image));
		}
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public String registrarFormaPago() {
		FacesMessage message = null;

		String redirect;
		int respuesta = fpDAO.validarRegistro(nombre);

		if (file.getFileName().equals("")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imagen Obligatoria", "Imagen Requerida");
			redirect = "inicio";
		} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
				&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido", "Imagen Invalida");
			redirect = "inicio";
		} else if (respuesta == 0) {
			fpDAO.insertar(nombre, file.getContents());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Forma de pago Registrada",
					"Forma de Pago Registrada");
			redirect = "inicio";
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este nombre ya existe. Pruebe con otro",
					"Nombre ya registrado");
			redirect = "inicio";
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}

	public String borrarFormaPago(int c) {
		FacesMessage message = null;

		String redirect;

		fpDAO.borrar(c);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Forma de Pago Borrada", "Forma de Pago Borrada");
		redirect = "inicio";

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}

}
