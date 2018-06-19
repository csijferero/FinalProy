package es.altair.managed;

import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.altair.bean.PDF;

@ManagedBean
public class FileDownloadManaged implements Serializable {

	public StreamedContent DownloadPDF(String uuid) {
		PDF pdf = new PDF(uuid);
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/certificate.pdf");
		return new DefaultStreamedContent(stream, "application/pdf", "Compra.pdf");
	}
}
