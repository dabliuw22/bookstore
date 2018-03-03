package com.leysoft.app.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.leysoft.app.entitys.User;
import com.leysoft.app.repositorys.UserRepository;
import com.leysoft.app.services.imple.UserServiceImp;

public class UserServiceTest {
	
	private static final Long id = new Long(1);
	
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImp userService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindById() {
		User userData = new User();
		when(userService.findById(id)).thenReturn(userData);
		User user = userService.findById(id);
		assertEquals(user, userData);
		verify(userRepository, times(1)).findOne(id);
	}

	@Test
	public void testFindAll() {
		List<User> usersData = new ArrayList<User>();
		usersData.add(new User());
		when(userService.findAll()).thenReturn(usersData);
		List<User> users = userService.findAll();
		assertEquals(users.size(), 1);
		verify(userRepository, times(1)).findAll();
	}
}
