package es.altair.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import es.altair.bean.Compras;
import es.altair.bean.Productos;
import es.altair.bean.Usuarios;
import es.altair.dao.FormaEnvioDAO;
import es.altair.dao.FormaEnvioIMPL;
import es.altair.dao.FormaPagoDAO;
import es.altair.dao.FormaPagoIMPL;
import es.altair.dao.ProductosDAO;
import es.altair.dao.ProductosIMPL;

@ManagedBean
@SessionScoped
public class ProductosManaged implements Serializable {

	ProductosDAO proDAO = new ProductosIMPL();

	private Integer cantidad;
	private Integer categoriaId;
	private Integer productoId;
	private String nombre;
	private String nombreOld;
	private Double precio;
	private String marca;
	private String modelo;
	private Integer ano;
	private Integer garantia;
	private String uuid;
	private UploadedFile file;
	private StreamedContent image;
	private Productos producto = new Productos();
	private List<Productos> productos = new ArrayList<Productos>();
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	

	private boolean response;

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getGarantia() {
		return garantia;
	}

	public void setGarantia(Integer garantia) {
		this.garantia = garantia;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Productos getProducto() {
		producto = proDAO.obtener(productoId);
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	public List<Productos> getProductos() {
		productos = proDAO.listado(categoriaId);
		return productos;
	}

	public void setProductos(List<Productos> productos) {
		this.productos = productos;
	}

	public Integer getProductoId() {
		return productoId;
	}

	public void setProductoId(Integer productoId) {
		this.productoId = productoId;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
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

	public String registrarProducto() {
		FacesMessage message = null;

		String redirect;
		int respuesta = proDAO.validarRegistro(nombre);

		if (file.getFileName().equals("")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imagen Obligatoria", "Imagen Requerida");
			redirect = "productos?faces-redirect=true&includeViewParams=true";
			response = true;
		} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
				&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido", "Imagen Invalida");
			redirect = "productos?faces-redirect=true&includeViewParams=true";
			response = true;
		} else if (respuesta == 0) {
			proDAO.insertar(categoriaId, nombre, marca, modelo, precio, garantia, ano, file.getContents(),
					UUID.randomUUID().toString());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Registrado", "Producto Registrado");
			redirect = "productos?faces-redirect=true&includeViewParams=true";
			response = false;
			clear();
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este nombre ya existe. Pruebe con otro",
					"Nombre ya registrado");
			redirect = "productos?faces-redirect=true&includeViewParams=true";
			response = true;
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}
	
	public String borrarProducto(int c) {
		FacesMessage message = null;

		String redirect;

		proDAO.borrar(c);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Borrado", "Producto Borrado");
		redirect = "productos?faces-redirect=true&includeViewParams=true";
		response = false;


		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		clear();
		return redirect;
	}
	
	
	
	public String editarProducto() {
		FacesMessage message = null;

		String redirect = "";

		int respuesta = proDAO.validarRegistro(nombre, nombreOld); // Comprobamos si es valido

		if (respuesta == 0) { // Es valido
			if (file.getFileName().equals("")) {
				proDAO.actualizarSinIMG(productoId, marca, modelo, precio, garantia, ano, nombre);
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Actualizado correctamente",
						"Producto Actualizado correctamente");
				redirect = "productos?faces-redirect=true&includeViewParams=true";
				clear();
			} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
					&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido",
						"Imagen Invalida");
				redirect = "productos?faces-redirect=true&includeViewParams=true";
			} else {
				proDAO.actualizar(productoId, marca, modelo, precio, garantia, ano, nombre, file.getContents());
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Actualizado correctamente",
						"Producto Actualizado correctamente");
				redirect = "productos?faces-redirect=true&includeViewParams=true";
				clear();
			}
		} else if (respuesta == 1) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya registrado. Pruebe con otro",
					"Email ya registrado");
			redirect = "productos?faces-redirect=true&includeViewParams=true";
		}
		
		response = false;

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return redirect;
	}

	public void cargaEdit(Productos pro) {
		productoId = pro.getIdproductos();
		nombre = pro.getNombre();
		nombreOld = pro.getNombre();
		marca = pro.getMarca();
		modelo = pro.getModelo();
		precio = pro.getPrecio();
		garantia = pro.getGarantia();
		ano = pro.getAno();
	}
	
	@PostConstruct
	public void init() {
		response = false;
		clear();
	}
	
	
	public void clear() {
		if(response==false) {
		setNombre(null);
		setMarca(null);
		setModelo(null);
		setPrecio(null);
		setGarantia(null);
		setAno(null);
		setFile(null);
		setCantidad(null);
		}
	}

}
