package com.leysoft.app.controllers;

import java.util.Locale;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leysoft.app.entitys.PasswordResetToken;
import com.leysoft.app.entitys.User;
import com.leysoft.app.services.imple.CustomUserDetailService;
import com.leysoft.app.services.inter.MailService;
import com.leysoft.app.services.inter.UserService;
import com.leysoft.app.utilitys.SecurityUtil;
import com.leysoft.app.utilitys.converters.inter.Converter;
import com.leysoft.app.utilitys.models.UserModel;
import com.leysoft.app.utilitys.validators.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private Converter<User, UserModel> userConverter;
	
	@Autowired
	private MailService mailService;
	
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
			User newUser = userConverter.modelToEntity(user);
			String password = SecurityUtil.randomPassword();
			userService.save(newUser, password);
			String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(newUser, token);
			String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			mailService.sendEmailHtml(url, request.getLocale(), token, newUser, password);
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
		UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("user", user);
		return "user/registro";
	}
	
	@PostMapping(value = "/edit")
	public String registro(@Valid @ModelAttribute("user") User user, BindingResult errors, Model model) {
		if(!errors.hasErrors()) {
			userService.update(user);
			return "redirect:/";
		}
		model.addAttribute("user", user);
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
				String password = SecurityUtil.randomPassword();
				user.setPassword(SecurityUtil.passwordEncoder().encode(password));
				userService.update(user);
				String token = UUID.randomUUID().toString();
				userService.createPasswordResetTokenForUser(user, token);
				String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				mailService.sendEmailHtml(url, request.getLocale(), token, user, password);
				redirect.addFlashAttribute("message", "Se ha enviado a tu e-mail un correo");
				return "redirect:/";
			}
			model.addAttribute("error", "E-mail no existe");
		return "user/reset_password";
	}
}