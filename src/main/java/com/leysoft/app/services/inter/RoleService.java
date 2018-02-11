package com.leysoft.app.services.inter;

import java.util.List;

import com.leysoft.app.entitys.Role;

public interface RoleService {
	
	public void save(Role role);
	
	public Role findById(Integer id);
	
	public Role findByNombre(String nombre);
	
	public List<Role> findAll();
	
	public void update(Role role);
	
	public void delete(Integer id);
}