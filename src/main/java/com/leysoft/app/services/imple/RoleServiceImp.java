package com.leysoft.app.services.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leysoft.app.entitys.Role;
import com.leysoft.app.repositorys.RoleRepository;
import com.leysoft.app.services.inter.RoleService;

@Service @Transactional
public class RoleServiceImp implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional
	@Override
	public void save(Role role) {
		roleRepository.save(role);
	}

	@Transactional(readOnly = true)
	@Override
	public Role findById(Integer id) {
		return roleRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Role findByNombre(String nombre) {
		return roleRepository.findByNombre(nombre);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Role> findAll() {
		return (List<Role>) roleRepository.findAll();
	}

	@Transactional
	@Override
	public void update(Role role) {
		roleRepository.save(role);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Role role = findById(id);
		if(role != null) {
			roleRepository.delete(id);
		}
	}
}