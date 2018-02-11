package com.leysoft.app.services.imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leysoft.app.entitys.User;
import com.leysoft.app.services.inter.UserService;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Username no existe");
		}
		return user;
	}
}