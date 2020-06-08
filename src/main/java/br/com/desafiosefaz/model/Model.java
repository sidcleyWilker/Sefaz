package br.com.desafiosefaz.model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Model implements Serializable{

	private static final long serialVersionUID = 1L;

	protected Integer id;
	protected LocalDate regdate;
	protected String regUser;
	protected String status;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDate getRegdate() {
		return regdate;
	}

	public void setRegdate(LocalDate regdate) {
		this.regdate = regdate;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
