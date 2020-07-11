package com.project.quizo.Service;

import com.project.quizo.Domain.UserManagement.User;
import com.project.quizo.Resource.UserChangePassDTO;
import com.project.quizo.Resource.UserRegisterDTO;

import java.util.List;

public interface UserService {

	User findById(Long id);
	
	User findByEmail(String email);

	User findByUsername(String username);

	User changePassword (UserChangePassDTO userChangePassDTO);

	User registerUser(UserRegisterDTO userRegisterDTO);

	List<User> getAllUsers();
	
}