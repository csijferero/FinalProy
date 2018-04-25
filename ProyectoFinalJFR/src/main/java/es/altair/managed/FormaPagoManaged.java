package es.altair.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.altair.bean.FormaPago;
import es.altair.dao.FormaPagoDAO;
import es.altair.dao.FormaPagoIMPL;

@ManagedBean
@SessionScoped
public class FormaPagoManaged implements Serializable {

	private List<FormaPago> formasDePago = new ArrayList<FormaPago>();

	public List<FormaPago> getFormasDePago() {
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

	@PostConstruct
	public void listadoo() {
		FormaPagoDAO fpDAO = new FormaPagoIMPL();
		formasDePago = fpDAO.listado();
	}

}
