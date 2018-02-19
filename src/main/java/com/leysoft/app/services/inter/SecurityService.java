package com.leysoft.app.services.inter;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.MailException;

import com.leysoft.app.entitys.User;
import com.leysoft.app.utilitys.models.UserModel;

public interface SecurityService {
	
	public void authentication(User user);
	
	public boolean isValid(String role);
	
	public void saveUser(UserModel user, HttpServletRequest request) throws MailException, MessagingException;
	
	public void resetPasswordUser(User user, HttpServletRequest request) throws MailException, MessagingException;
}