package com.leysoft.app.repositorys;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.leysoft.app.entitys.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
}
