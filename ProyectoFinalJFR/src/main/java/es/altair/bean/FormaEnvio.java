package es.altair.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "formaenvio")
public class FormaEnvio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idformaenvio;
	private String nombre;
	private byte[] image;

	@OneToMany(mappedBy = "formaEnvio", cascade = CascadeType.ALL)
	private Set<Compras> comprasEnvio;

	public FormaEnvio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormaEnvio(String nombre, byte[] image) {
		super();
		this.nombre = nombre;
		this.image = image;
	}

	public int getIdformaenvio() {
		return idformaenvio;
	}

	public void setIdformaenvio(int idformaenvio) {
		this.idformaenvio = idformaenvio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Set<Compras> getComprasEnvio() {
		return comprasEnvio;
	}

	public void setComprasEnvio(Set<Compras> comprasEnvio) {
		this.comprasEnvio = comprasEnvio;
	}

}
