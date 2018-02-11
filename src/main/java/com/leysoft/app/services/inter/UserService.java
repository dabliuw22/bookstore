package com.leysoft.app.services.inter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.leysoft.app.entitys.PasswordResetToken;
import com.leysoft.app.entitys.User;

public interface UserService {
	
	public void save(User user);
	
	public void save(User user, String password);
	
	public User findById(Long id);
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public List<User> findAll();
	
	public Page<User> findAll(Pageable pageable);
	
	public void update(User user);
	
	public void delete(Long id);
	
	public PasswordResetToken getPasswordResetToken(String token);
	
	public void createPasswordResetTokenForUser(User user, String token);
}
