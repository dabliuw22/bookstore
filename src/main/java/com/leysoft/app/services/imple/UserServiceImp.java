package com.leysoft.app.services.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leysoft.app.entitys.PasswordResetToken;
import com.leysoft.app.entitys.Role;
import com.leysoft.app.entitys.User;
import com.leysoft.app.repositorys.PasswordResetTokenRepository;
import com.leysoft.app.repositorys.UserRepository;
import com.leysoft.app.services.inter.RoleService;
import com.leysoft.app.services.inter.UserService;
import com.leysoft.app.utilitys.SecurityUtil;

@Service @Transactional
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Transactional
	@Override
	public void save(User user) {
		String password = SecurityUtil.passwordEncoder().encode(user.getPassword());
		user.setPassword(password);
		Role rol = roleService.findById(1);
		user.addRole(rol);
		userRepository.save(user);
	}
	
	public void save(User user, String password) {
		user.setPassword(SecurityUtil.passwordEncoder().encode(password));
		Role rol = roleService.findById(1);
		user.addRole(rol);
		userRepository.save(user);
	}

	@Transactional(readOnly = true) 
	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional(readOnly = true)
	@Override
	public User findByUsernameJoinFetch(String username) {
		return userRepository.findByUsernameJoinFetch(username);
	}
	
	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Transactional(readOnly = true)
	@Override
	public User findByEmailJoinFetch(String email) {
		return userRepository.findByEmailJoinFetch(email);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public void update(User user) {
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		User user = findById(id);
		if(user != null) {
			userRepository.delete(id);
		}
	}

	@Transactional
	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Transactional
	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(passwordResetToken);
	}
}