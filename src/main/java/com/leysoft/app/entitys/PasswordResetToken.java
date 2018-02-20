package com.leysoft.app.entitys;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name = "password_token")
public class PasswordResetToken implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final int EXPIRATION = 60*24;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String token;
	
	@Column
	private boolean activo;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "expired_at")
	private Date expireAt;

	public PasswordResetToken() {}

	public PasswordResetToken(final String token, final User user) {
		this.token = token;
		this.user = user;
		this.activo = true;
		this.expireAt = calculate(EXPIRATION);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
		this.expireAt = calculate(EXPIRATION);
	}

	public User getUser() {
		return user;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Date getExpireAt() {
		return expireAt;
	}
	
	private Date calculate(final int EXPIRATION) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, EXPIRATION);
		return new Date(calendar.getTime().getTime());
	}
}