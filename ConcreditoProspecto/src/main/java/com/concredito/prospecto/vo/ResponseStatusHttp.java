package com.concredito.prospecto.vo;

public class ResponseStatusHttp {

	private int code;
	private String tittle;
	private String message;
	private String messages;
	private boolean success;
	private Object data;

	public ResponseStatusHttp() {
	}

	public ResponseStatusHttp(int code, String tittle, String message, String messages, boolean success, Object data) {
		super();
		this.code = code;
		this.tittle = tittle;
		this.message = message;
		this.messages = messages;
		this.success = success;
		this.data = data;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseStatusHttp [code=" + code + ", tittle=" + tittle + ", message=" + message + ", success="
				+ success + "]";
	}

	public String toJSON() {
		return "{code=" + code + ", tittle='" + tittle + "', message='" + message + "', messages=" + messages + ", success=" + success + "}";
	}

}
