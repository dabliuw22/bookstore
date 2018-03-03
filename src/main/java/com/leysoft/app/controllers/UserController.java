package com.leysoft.app.controllers;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leysoft.app.entitys.PasswordResetToken;
import com.leysoft.app.entitys.User;
import com.leysoft.app.services.inter.PasswordResetTokenService;
import com.leysoft.app.services.inter.SecurityService;
import com.leysoft.app.services.inter.UserService;
import com.leysoft.app.utilitys.models.UserModel;
import com.leysoft.app.utilitys.validators.UserValidator;

@SessionAttributes(value = "currentUser")
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping(value = "/add")
	public String add(Model model) {
		model.addAttribute("user", new UserModel());
		return "user/create";
	}
	
	@PostMapping(value = "/add")
	public String add(@Valid @ModelAttribute("user") UserModel user, BindingResult errors,
			Model model, HttpServletRequest request, RedirectAttributes redirect) throws MailException, MessagingException {
		userValidator.validate(user, errors);
		if(!errors.hasErrors()) {
			securityService.saveUser(user, request);
			redirect.addFlashAttribute("message", "Se ha enviado a tu e-mail un correo para continuar el proceso de registro");
			return "redirect:/";
		}
		model.addAttribute("user", user);
		return "user/create";
	}
	
	@GetMapping(value = "/registro")
	public String registro(Locale locale, @RequestParam("token") String token, Model model, RedirectAttributes redirect) {
		PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
		if(passwordResetToken == null || !passwordResetToken.isActivo()) {
			redirect.addFlashAttribute("error", "Token invalido");
			return "redirect:/add";
		}
		User user = passwordResetToken.getUser();
		securityService.authentication(user);
		model.addAttribute("currentUser", user);
		return "user/registro";
	}
	
	@PostMapping(value = "/edit")
	public String registro(@Valid @ModelAttribute("currentUser") User user, BindingResult errors,
			Model model, SessionStatus status) {
		if(!errors.hasErrors()) {
			PasswordResetToken passwordResetToken = passwordResetTokenService.findByUser(user);
			userService.update(user);
			status.setComplete();
			passwordResetTokenService.disable(passwordResetToken);
			return "redirect:/";
		}
		model.addAttribute("currentUser", user);
		return "user/registro";
	}
	
	@GetMapping(value = "/reset-password")
	public String resetPassword() {
		return "user/reset_password";
	}
	
	@PostMapping(value = "/reset-password")
	public String resetPassword(@Valid @RequestParam(value = "email", required = false) String email,
			Model model, HttpServletRequest request, RedirectAttributes redirect) throws MailException, MessagingException {
			User user = userService.findByEmail(email);
			if(user != null) {
				securityService.resetPasswordUser(user, request);
				redirect.addFlashAttribute("message", "Se ha enviado a tu e-mail un correo");
				return "redirect:/";
			}
			model.addAttribute("error", "E-mail no existe");
		return "user/reset_password";
	}
}