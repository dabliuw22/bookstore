package com.leysoft.app.services.inter;

import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.leysoft.app.entitys.User;

public interface MailService {
	
	public void sendEmailText(String url, Locale locale, String token, User user, String password);
	
	public void sendEmailHtml(String url, Locale locale, String token, User user, String password)  throws MessagingException, MailException;
}