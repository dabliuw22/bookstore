package com.leysoft.app.services.inter;

import java.util.Date;
import java.util.List;

import com.leysoft.app.entitys.PasswordResetToken;
import com.leysoft.app.entitys.User;

public interface PasswordResetTokenService {
	
	public PasswordResetToken findById(Long id);
	
	public PasswordResetToken findByToken(String token);
	
	public PasswordResetToken findByUser(User user);
	
	public List<PasswordResetToken> findAllByExpireAtLessThan(Date now);
	
	public void disable(PasswordResetToken passwordResetToken);
	
	public void update(PasswordResetToken passwordResetToken);
	
	public void detelteAllExpiredSince();
}
