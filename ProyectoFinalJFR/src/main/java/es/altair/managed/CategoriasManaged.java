package es.altair.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.altair.bean.Categorias;
import es.altair.dao.CategoriasDAO;
import es.altair.dao.CategoriasIMPL;

@ManagedBean
@SessionScoped
public class CategoriasManaged {

	private List<Categorias> categorias = new ArrayList<Categorias>();

	public List<Categorias> getCategorias() {
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

	@PostConstruct
	public void listadoo() {
		CategoriasDAO catDAO = new CategoriasIMPL();
		categorias = catDAO.listado();
	}

}
