package com.leysoft.app.utilitys.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.leysoft.app.services.inter.UserService;
import com.leysoft.app.utilitys.models.UserModel;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserModel user = (UserModel) obj;
		if(user != null) {
			if(userService.findByUsername(user.getUsername()) != null) {
				errors.rejectValue("username", "user.username", "Username ya existente, intenta con otro");
			}
			if(userService.findByUsername(user.getUsername()) != null) {
				errors.rejectValue("email", "user.email", "E-mail ya existente, intenta con otro");
			}
		}
	}
}