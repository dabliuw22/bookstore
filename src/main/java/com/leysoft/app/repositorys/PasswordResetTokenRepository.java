package com.leysoft.app.repositorys;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.leysoft.app.entitys.PasswordResetToken;
import com.leysoft.app.entitys.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	
	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByUser(User user);
	
	List<PasswordResetToken> findAllByExpireAtLessThan(Date now);
	
	@Modifying
	@Query("delete from PasswordResetToken as t where t.expireAt <= ?1 or t.activo = false")
	void detelteAllExpiredSince(Date now);
}