package es.altair.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import es.altair.bean.Mail;
import es.altair.bean.PDF;
import es.altair.bean.TipoUsuarios;
import es.altair.bean.Usuarios;
import es.altair.dao.TipoUsuarioDAO;
import es.altair.dao.TipoUsuarioIMPL;
import es.altair.dao.UsuariosDAO;
import es.altair.dao.UsuariosIMPL;

@ManagedBean
public class UsuariosManaged implements Serializable {

	UsuariosDAO usuDAO = new UsuariosIMPL();
	TipoUsuarioDAO tipoDAO = new TipoUsuarioIMPL();

	private Integer idUsuario;
	private String nick;
	private String nickOld;
	private String nombre;
	private String apellidos;
	private String direccion;
	private String contacto;
	private String dni;
	private StreamedContent image;
	private UploadedFile file;
	private String correo;
	private String correoOld;
	private String contrasena;
	private String nuevaContraseña;
	private Integer nivelAcceso;
	private TipoUsuarios tipoUsuario;
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TipoUsuarios getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuarios tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(Integer nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNickOld() {
		return nickOld;
	}

	public void setNickOld(String nickOld) {
		this.nickOld = nickOld;
	}

	public String getCorreoOld() {
		return correoOld;
	}

	public void setCorreoOld(String correoOld) {
		this.correoOld = correoOld;
	}

	public String getNuevaContraseña() {
		return nuevaContraseña;
	}

	public void setNuevaContraseña(String nuevaContraseña) {
		this.nuevaContraseña = nuevaContraseña;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UsuariosDAO getUsuDAO() {
		return usuDAO;
	}

	public void setUsuDAO(UsuariosDAO usuDAO) {
		this.usuDAO = usuDAO;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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
			String id = context.getExternalContext().getRequestParameterMap().get("imageeID");
			byte[] imagen = usuDAO.getfpImage(Integer.valueOf(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(imagen));
		}
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String activarUsuario() {
		FacesMessage message = null;
		int respuesta = usuDAO.activarUsuario(uuid);
		String redirect;

		if (respuesta == 0) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Link de activacion Invalido", "Link Invalido");
		} else if (respuesta == 1) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta ya activada", "Link Utilizado");
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta activada correctamente. Inicie Sesion",
					"Link Aceptado");
		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return "inicio?faces-redirect=true";
	}

	public String comprobarUsuario() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		FacesMessage message = null;
		Usuarios usuario = usuDAO.comprobarUsuario(correo, contrasena);
		String redirect;

		if (usuario != null && usuario.getTipoUsuarios().getIdtipousuarios() != 3) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión Iniciada", "Sesión Iniciada");
			session.setAttribute("usuario", usuario);
			redirect = "inicio?faces-redirect=true";
		} else if (usuario != null && usuario.getTipoUsuarios().getIdtipousuarios() ==3) {
			redirect = "log";
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Cuenta no Activada. Revise su correo electronico",
					"Invalid credentials");
		}
		else {
			redirect = "log";
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o Contraseña Invalidos",
					"Invalid credentials");
		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, message);

		return redirect;
	}

	public String registrarUsuario() {
		FacesMessage message = null;

		String redirect;
		int respuesta = usuDAO.validarRegistro(correo, nick);

		if (file.getFileName().equals("")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imagen Obligatoria", "Imagen Requerida");
			redirect = "registro";
		} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
				&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido", "Imagen Invalida");
			redirect = "registro";
		} else if (respuesta == 0) {
			String uuid = UUID.randomUUID().toString();
			usuDAO.insertar(nick, correo, contrasena, nombre, apellidos, direccion, Double.parseDouble(contacto), dni,
					file.getContents(), uuid);
			String url = "http://enjutojose.es:8080/ProyectoFinalJFR/404.jsf?u=" + uuid;
			 new Mail(correo, "Correo de Confirmación", "Active su cuenta haciendo click en la siguiente dirección " + url);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Usuario Registrado Correctamente. Active su cuenta con el correo electronico enviado.",
					"Usuario Registrado");
			redirect = "log?faces-redirect=true";

		} else if (respuesta == 1) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ya registrado. Pruebe con otro",
					"Email ya registrado");
			redirect = "registro";
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nick ya registrado. Pruebe con otro",
					"Nick ya registrado");
			redirect = "registro";
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return redirect;
	}

	public String editarUsuario() {
		FacesMessage message = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		String redirect = "";

		if (usuDAO.comprobarUsuario(correoOld, contrasena) == null) { // Clave incorrecta
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clave Incorrecta", "Clave Incorrecta");
			redirect = "inicio";
		} else {

			int respuesta = usuDAO.validarRegistro(correo, nick, correoOld, nickOld); // Comprobamos si es valido

			if (respuesta == 0) { // Es valido
				if (file.getFileName().equals("")) {
					usuDAO.actualizarSinIMG(idUsuario, nick, correo, contrasena, nombre, apellidos, direccion,
							Double.parseDouble(contacto), dni, nivelAcceso);
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Actualizado correctamente",
							"Usuario Actualizado correctamente");
					redirect = "inicio";
					// Poner en sesion
					if (((Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("usuario")).getIdusuarios() == idUsuario) {
						session.setAttribute("usuario", usuDAO.comprobarUsuario(correo, contrasena));
					}
				} else if (!file.getFileName().endsWith("jpg") && !file.getFileName().endsWith("jpeg")
						&& !file.getFileName().endsWith("png") && !file.getFileName().endsWith("gif")) {
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de imagen invalido",
							"Imagen Invalida");
					redirect = "inicio";
				} else {
					usuDAO.actualizar(idUsuario, nick, correo, contrasena, nombre, apellidos, direccion,
							Double.parseDouble(contacto), dni, file.getContents(), nivelAcceso);
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Actualizado correctamente",
							"Usuario Actualizado correctamente");
					redirect = "inicio";
					// Poner en sesion
					if (((Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("usuario")).getIdusuarios() == idUsuario) {
						session.setAttribute("usuario", usuDAO.comprobarUsuario(correo, contrasena));
					}
				}
			} else if (respuesta == 1) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ya registrado. Pruebe con otro",
						"Email ya registrado");
				redirect = "inicio";
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nick ya registrado. Pruebe con otro",
						"Nick ya registrado");
				redirect = "inicio";
			}
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return redirect;
	}

	public String logout() {
		//new PDF();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesMessage message = null;
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesion Cerrada", "Sesion Cerrada");
		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return "log?faces-redirect=true";
	}

	public void cargaEdit() {
		Usuarios usu = ((Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("usuario"));
		idUsuario = usu.getIdusuarios();
		correo = usu.getEmail();
		nombre = usu.getNombre();
		nick = usu.getNick();
		apellidos = usu.getApellidos();
		direccion = usu.getDireccion();
		contacto = String.format("%.0f", usu.getContacto());
		dni = usu.getDni();
		correoOld = usu.getEmail();
		nickOld = usu.getNick();
		nivelAcceso = usu.getTipoUsuarios().getIdtipousuarios();
	}
}
