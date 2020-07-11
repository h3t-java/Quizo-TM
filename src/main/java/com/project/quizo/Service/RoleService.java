package com.project.quizo.Service;

import com.project.quizo.Domain.UserManagement.Role;

import java.util.List;

public interface RoleService {
	
	List<Role> findAllRoles();

	List<Role> findAllByIds(List<Long> rolesIds);

}