package com.leysoft.app.utilitys.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserModel {

	@NotEmpty
	private String username;
	
	@Email
	@NotEmpty
	private String email;
	
	public UserModel() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}