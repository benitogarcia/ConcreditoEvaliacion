package com.concredito.prospecto.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProspectoUpdateStatus {

	@Positive
	private Long id;

	@NotNull
	private boolean status;

	private String observacion;

	public ProspectoUpdateStatus() {
	}

	public ProspectoUpdateStatus(Long id, boolean status, String observacion) {
		this.id = id;
		this.status = status;
		this.observacion = observacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Override
	public String toString() {
		return "ProspectoUpdateStatus [id=" + id + ", status=" + status + ", observacion=" + observacion + "]";
	}

}
