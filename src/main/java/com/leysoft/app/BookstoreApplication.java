package com.leysoft.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.leysoft.app.entitys.Role;
import com.leysoft.app.services.inter.RoleService;

@EnableAsync
@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private RoleService roleService;
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Role roleUser = new Role();
		roleUser.setNombre("ROLE_USER");
		roleService.save(roleUser);
		Role roleAdmin = new Role();
		roleAdmin.setNombre("ROLE_ADMIN");
		roleService.save(roleAdmin);
	}
}
