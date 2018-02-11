package com.leysoft.app.repositorys;

import org.springframework.data.repository.CrudRepository;

import com.leysoft.app.entitys.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	
	public Role findByNombre(String nombre);
}
