package com.leysoft.app.utilitys.converters.imple;

import org.springframework.stereotype.Component;

import com.leysoft.app.entitys.User;
import com.leysoft.app.utilitys.converters.inter.Converter;
import com.leysoft.app.utilitys.models.UserModel;

@Component
public class UserConverter implements Converter<User, UserModel> {

	@Override
	public User modelToEntity(UserModel model) {
		User user = new User();
		if(model != null) {
			user.setUsername(model.getUsername());
			user.setEmail(model.getEmail());
		}
		return user;
	}

	@Override
	public UserModel entityToModel(User entity) {
		UserModel user = new UserModel();
		if(entity != null) {
			user.setUsername(entity.getUsername());
			user.setEmail(entity.getEmail());
		}
		return user;
	}	
}