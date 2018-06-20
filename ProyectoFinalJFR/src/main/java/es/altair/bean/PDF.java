package es.altair.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import es.altair.dao.ComprasDAO;
import es.altair.dao.ComprasIMPL;

public class PDF {

	ComprasDAO cpDAO = new ComprasIMPL();

	public PDF() {
		try {
			PdfReader reader;
			reader = new PdfReader("/resources/PDF.pdf");
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/resources/certificate.pdf"));
			AcroFields form = stamper.getAcroFields();
			form.setField("NombreEmisor", "José Antonio FR");
			form.setField("DNIEmisor", "25458458R");
			form.setField("DirecciónEmisor", "Avd/ Simon Bolivar");
			form.setField("EmailEmisor", "enjutojose@hotmail.com");
			form.setField("MovilEmisor", "699735393");
			stamper.setFormFlattening(true);
			stamper.close();

		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PDF(String uuid) {
		try {
			Compras compras = cpDAO.getComprasByUuid(uuid);
			PdfReader reader;
			reader = new PdfReader("/resources/PDF.pdf");
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/opt/tomcat/webapps/ProyectoFinalJFR/resources/certificate.pdf"));
			//PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("certificate.pdf"));
			AcroFields form = stamper.getAcroFields();
			form.setField("NombreEmisor", "José Antonio FR");
			form.setField("DNIEmisor", "25458458R");
			form.setField("DirecciónEmisor", "Avd/ Simon Bolivar");
			form.setField("EmailEmisor", "enjutojose@hotmail.com");
			form.setField("MovilEmisor", "699735393");
			form.setField("NombreDestino",
					compras.getUsuario().getNombre() + " " + compras.getUsuario().getApellidos());
			form.setField("DNIDestino", compras.getUsuario().getDni());
			form.setField("DireccionDestino", compras.getUsuario().getDireccion());
			form.setField("EmailDestino", compras.getUsuario().getEmail());
			form.setField("MovilDestino", String.format("%.0f", compras.getUsuario().getContacto()));
			form.setField("Fecha", compras.getFecha().getDate() + "/" + (compras.getFecha().getMonth() + 1) + "/"
					+ (compras.getFecha().getYear() + 1900));
			form.setField("Envio", compras.getFormaEnvio().getNombre());
			form.setField("Pago", compras.getFormaPago().getNombre());
			form.setField("Factura", compras.getUuid());
			int count = 0;
			double total = 0;
			for (Compras_Productos compra : compras.getComprasProductos()) {
				count++;
				form.setField("c" + count, String.valueOf(compra.getCantidad()));
				form.setField("d" + count, compra.getProducto().getNombre());
				form.setField("p" + count, String.valueOf(compra.getProducto().getPrecio()) + "€");
				total += compra.getProducto().getPrecio() * compra.getCantidad();
				form.setField("t" + count, String.valueOf(compra.getProducto().getPrecio() * compra.getCantidad()) + "€");
			}
			form.setField("PrecioTotal", String.valueOf(total)+"€");
			stamper.setFormFlattening(true);
			stamper.close();

		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}