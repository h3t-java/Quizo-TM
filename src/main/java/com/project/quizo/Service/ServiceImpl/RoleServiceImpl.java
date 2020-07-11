package com.project.quizo.Service.ServiceImpl;

import java.util.List;

import com.project.quizo.Domain.UserManagement.Role;
import com.project.quizo.Repository.RoleRepository;
import com.project.quizo.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

	private static final String ROLES_NOT_FOUND_FORMAT = "Roles with ids %s not found.";
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> findAllByIds(List<Long> rolesIds) {
		return roleRepository.findByIdIn(rolesIds);
	}
	
}