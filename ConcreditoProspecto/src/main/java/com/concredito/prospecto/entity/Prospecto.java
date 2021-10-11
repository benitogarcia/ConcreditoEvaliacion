package com.concredito.prospecto.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "prospectos")
public class Prospecto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "Nombre es obligatorio.")
	@Size(min = 3, max = 25, message = "Nombre debe contener de 3 a 25 caracteres.")
	@Pattern(regexp = "^[a-zA-Z0]+( [a-zA-Z]+)*$", message = "Nombre solo debe contener [aA-zZ].")
	private String nombre;

	@NotNull(message = "Primer Apellido es obligatorio.")
	@Size(min = 3, max = 25, message = "Primer Apellido debe contener de 3 a 25 caracteres.")
	@Pattern(regexp = "^[a-zA-Z0]+( [a-zA-Z]+)*$", message = "Primer Apellido solo debe contener [aA-zZ].")
	private String primerApellido;
	private String segundoApellido;

	@NotNull(message = "Calle es obligatorio.")
	@Size(min = 2, max = 25, message = "Calle debe contener de 2 a 25 caracteres.")
	private String calle;

	@PositiveOrZero(message = "Número debe ser de 0 a N, positivo.")
	private int numero;

	@NotNull(message = "Colonia es obligatorio.")
	@Size(min = 3, max = 25, message = "Colonia debe contener de 3 a 50 caracteres.")
	private String colonia;

	@Pattern(regexp = "^[0-9]{5,6}$", message = "Código Postal debe contener 5 a 6 digitos [0-9].")
	private String codigoPostal;

	@Pattern(regexp = "[0-9]{10}", message = "Télefono solo debe contener 10 digitos[0-9].")
	private String telefono;

	@Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?$", message = "Formato RFC invalido.")
	private String rfc;

	@Transient
	private MultipartFile[] documentos;

	private String rutaDocs;

	private String status;

	private String observacion;

	public Prospecto() {
		this.nombre = "JOSE BENITO";
		this.primerApellido = "GARCIA";
		this.segundoApellido = "SOLANO";
		this.calle = "SAN CRISTOBAL";
		this.colonia = "EMILIANO ZAPATA";
		this.codigoPostal = "12345";
		this.telefono = "1234567890";
		this.rfc = "GASB910509HJ0";
	}

	public Prospecto(long id, String nombre, String primerApellido, String segundoApellido, String calle, int numero,
			String colonia, String codigoPostal, String telefono, String rfc, MultipartFile[] documentos,
			String status) {
		this.id = id;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.calle = calle;
		this.numero = numero;
		this.colonia = colonia;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.rfc = rfc;
		this.documentos = documentos;
		this.status = status;
	}

	public Prospecto(long id, String nombre, String primerApellido, String segundoApellido, String calle, int numero,
			String colonia, String codigoPostal, String telefono, String rfc, String rutaDocs, String status) {
		this.id = id;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.calle = calle;
		this.numero = numero;
		this.colonia = colonia;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.rfc = rfc;
		this.rutaDocs = rutaDocs;
		this.status = status;
	}

	public Prospecto(long id, String nombre, String primerApellido, String segundoApellido, String calle, int numero,
			String colonia, String codigoPostal, String telefono, String rfc, MultipartFile[] documentos,
			String rutaDocs, String status, String observacion) {
		this.id = id;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.calle = calle;
		this.numero = numero;
		this.colonia = colonia;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.rfc = rfc;
		this.documentos = documentos;
		this.rutaDocs = rutaDocs;
		this.status = status;
		this.observacion = observacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public MultipartFile[] getDocumentos() {
		return documentos;
	}

	public void setDocumentos(MultipartFile[] documentos) {
		this.documentos = documentos;
	}

	public String getRutaDocs() {
		return rutaDocs;
	}

	public void setRutaDocs(String rutaDocs) {
		this.rutaDocs = rutaDocs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prospecto other = (Prospecto) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prospecto [id=" + id + ", nombre=" + nombre + ", primerApellido=" + primerApellido
				+ ", segundoApellido=" + segundoApellido + ", calle=" + calle + ", numero=" + numero + ", colonia="
				+ colonia + ", codigoPostal=" + codigoPostal + ", telefono=" + telefono + ", rfc=" + rfc + ", rutaDocs="
				+ rutaDocs + ", status=" + status + ", observacion=" + observacion + "]";
	}

}
