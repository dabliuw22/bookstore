package com.leysoft.app.repositorys;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leysoft.app.entitys.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public User findByUsername(String username);
	
	@Query("select u from User as u left join fetch u.roles as r where u.username = ?1")
	public User findByUsernameJoinFetch(String username);
	
	public User findByEmail(String email);
	
	@Query("select u from User as u left join fetch u.roles as r where u.email = ?1")
	public User findByEmailJoinFetch(String email);
}