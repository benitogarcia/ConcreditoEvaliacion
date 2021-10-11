package com.concredito.prospecto.vo;

public class Documento {

	private String type;
	private String name;

	public Documento() {
	}

	public Documento(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Documento [type=" + type + ", name=" + name + "]";
	}

}
