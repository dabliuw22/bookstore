package com.leysoft.app.services.imple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leysoft.app.entitys.PasswordResetToken;
import com.leysoft.app.entitys.User;
import com.leysoft.app.repositorys.PasswordResetTokenRepository;
import com.leysoft.app.services.inter.PasswordResetTokenService;

@Service @Transactional
public class PasswordResetTokenServiceImp implements PasswordResetTokenService {

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository; 
	
	@Transactional(readOnly = true)
	@Override
	public PasswordResetToken findById(Long id) {
		return passwordResetTokenRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public PasswordResetToken findByToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Transactional(readOnly = true)
	@Override
	public PasswordResetToken findByUser(User user) {
		return passwordResetTokenRepository.findByUser(user);
	}

	@Transactional(readOnly = true)
	@Override
	public List<PasswordResetToken> findAllByExpireAtLessThan(Date now) {
		return passwordResetTokenRepository.findAllByExpireAtLessThan(now);
	}

	@Scheduled(initialDelayString = "${scheduled.initial.delay}", fixedRateString = "${scheduled.rate}")
	@Transactional
	@Override
	public void detelteAllExpiredSince() {
		Date now = new Date();
		if(now != null) {
			passwordResetTokenRepository.detelteAllExpiredSince(now);
		}
	}
}