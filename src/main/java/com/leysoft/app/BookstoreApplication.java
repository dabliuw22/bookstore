package com.leysoft.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.leysoft.app.entitys.Role;
import com.leysoft.app.services.imple.RoleServiceImp;
import com.leysoft.app.services.imple.UploadFileServiceImp;
import com.leysoft.app.services.inter.RoleService;
import com.leysoft.app.services.inter.UploadFileService;


/**
 * Esta clase define la configuración de arranque para el proyecto.
 * 
 * @author will
 */

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * Método main para el arranque de la app spring-boot.
	 * 
	 * @param args array de String que recibe el método statico.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	/**
	 * Método que se debe a la implementación de la interface CommandLineRunner,
	 * el cual se ejecutara después que el contexto se construya.
	 * 
	 * @param arg0 String que recibe el método.
	 */
	@Override
	public void run(String... arg0) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
		
		Role roleUser = new Role();
		roleUser.setNombre("ROLE_USER");
		roleService.save(roleUser);
		Role roleAdmin = new Role();
		roleAdmin.setNombre("ROLE_ADMIN");
		roleService.save(roleAdmin);
	}
	
	/**
	 * Bean para agregar UploadFileService al contexto de Spring.
	 * 
	 * @return La implementación UploadFileServiceImp.
	 */
	@Bean
	public UploadFileService uploadFileService() {
		return new UploadFileServiceImp();
	}
	
	/**
	 * Bean para agregar RoleService al contexto de Spring.
	 * 
	 * @return La implementación RoleServiceImp.
	 */
	@Bean
	public RoleService roleService() {
		return new RoleServiceImp();
	}
}
