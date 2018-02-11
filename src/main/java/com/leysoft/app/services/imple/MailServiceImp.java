package com.leysoft.app.services.imple;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.leysoft.app.entitys.User;
import com.leysoft.app.services.inter.MailService;

@Service
public class MailServiceImp implements MailService {

	@Value("${support.email}")
	private String from;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Async
	@Override
	public void sendEmailText(String url, Locale locale, String token, User user, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		url += "/registro?token=" + token;
		String text = "\nClick en el link para verifivar tu cuente y editar tus credenciales. Tu password es:\n";
		text += password;
		message.setTo(user.getEmail());
		message.setSubject("BookStore - Nuevo Usuario");
		message.setText(text);
		message.setFrom(from);
		mailSender.send(message);
	}

	@Async
	@Override
	public void sendEmailHtml(String url, Locale locale, String token, User user, String password)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		Context context = new Context();
		url += "/registro?token=" + token;
		context.setVariable("username", user.getUsername());
		context.setVariable("link", url);
		context.setVariable("password", password);
		String html = templateEngine.process("email/registro", context);
		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
		helper.setTo(user.getEmail());
		helper.setSubject("BookStore - Nuevo Usuario");
		helper.setText(html, true);
		helper.setFrom(from);
		mailSender.send(message);
	}
}