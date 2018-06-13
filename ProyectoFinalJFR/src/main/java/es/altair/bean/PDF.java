package es.altair.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PDF {

	public PDF() {
		try {
			PdfReader reader;
			reader = new PdfReader(
					"C:\\Users\\JoseAntonio\\git\\FinalProy\\ProyectoFinalJFR\\src\\main\\resources\\PDF.pdf");
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"C:\\Users\\JoseAntonio\\git\\FinalProy\\ProyectoFinalJFR\\src\\main\\resources\\certificate.pdf"));
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
}