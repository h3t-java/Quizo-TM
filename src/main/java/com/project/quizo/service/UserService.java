package com.project.quizo.service;

import com.project.quizo.domain.userManagement.User;
import com.project.quizo.resource.UserChangePassDTO;
import com.project.quizo.resource.UserRegisterDTO;

import java.util.List;

public interface UserService {

	User findById(Long id);
	
	User findByEmail(String email);

	User findByUsername(String username);

	User changePassword (UserChangePassDTO userChangePassDTO);

	User registerUser(UserRegisterDTO userRegisterDTO);

	List<User> getAllUsers();
	
}