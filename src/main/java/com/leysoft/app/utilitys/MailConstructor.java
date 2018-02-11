package com.leysoft.app.utilitys;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.leysoft.app.entitys.User;

@Component
public class MailConstructor {
	
	@Value("${support.email}")
	private String count;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public SimpleMailMessage constructResetTokenEmail(String url, Locale locale, String token, User user, String password) {
		SimpleMailMessage email = new SimpleMailMessage();
		url += "/registro?token=" + token;
		String message = "\nClick en el link para verifivar tu cuente y editar tus credenciales. Tu password es:\n";
		message += password;
		email.setTo(user.getEmail());
		email.setSubject("BookStore - Nuevo Usuario");
		email.setText(url + message);
		email.setFrom(count);
		return email;
	}
	
	public MimeMessage constructResetTokenEmail(String url, Locale locale, String token, User user, 
			String password, JavaMailSender mailSender) throws MessagingException {
		MimeMessage email = mailSender.createMimeMessage();
		Context context = new Context();
		url += "/registro?token=" + token;
		context.setVariable("username", user.getUsername());
		context.setVariable("link", url);
		context.setVariable("password", password);
		String html = templateEngine.process("email/registro", context);
		MimeMessageHelper helper = new MimeMessageHelper(email, "UTF-8");
		helper.setTo(user.getEmail());
		helper.setSubject("BookStore - Nuevo Usuario");
		helper.setText(html, true);
		helper.setFrom(count);
		return email;
	}
}