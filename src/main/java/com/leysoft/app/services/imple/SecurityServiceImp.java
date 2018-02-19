package com.leysoft.app.services.imple;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.leysoft.app.entitys.User;
import com.leysoft.app.services.inter.MailService;
import com.leysoft.app.services.inter.SecurityService;
import com.leysoft.app.services.inter.UserService;
import com.leysoft.app.utilitys.SecurityUtil;
import com.leysoft.app.utilitys.converters.inter.Converter;
import com.leysoft.app.utilitys.models.UserModel;

@Service
public class SecurityServiceImp implements SecurityService {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Converter<User, UserModel> userConverter;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public void authentication(User user) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	@Override
	public boolean isValid(String role) {
		boolean valid = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.isAuthenticated()) {
			for(GrantedAuthority authority: auth.getAuthorities()) {
				if(authority.getAuthority().equals(role)) {
					valid = true;
				}
			}
		}
		return valid;
	}
	
	@Override
	public void saveUser(UserModel user, HttpServletRequest request) throws MailException, MessagingException {
		User newUser = userConverter.modelToEntity(user);
		String password = SecurityUtil.randomPassword();
		userService.save(newUser, password);
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(newUser, token);
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		mailService.sendEmailHtml(url, request.getLocale(), token, newUser, password);
	}
	
	@Override
	public void resetPasswordUser(User user, HttpServletRequest request) throws MailException, MessagingException {
		String password = SecurityUtil.randomPassword();
		user.setPassword(SecurityUtil.passwordEncoder().encode(password));
		userService.update(user);
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		mailService.sendEmailHtml(url, request.getLocale(), token, user, password);
	}
}