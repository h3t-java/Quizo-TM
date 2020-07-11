package com.project.quizo.Resource;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.project.quizo.Domain.UserManagement.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserDTO {

	@ApiModelProperty(value = "Database generated user id", position = 0, example = "1")
	private Long id;

	@ApiModelProperty(value = "User's first name", position = 1, example = "John")
	private String firstName;

	@ApiModelProperty(value = "User's last name", position = 2, example = "Doe")
	private String lastName;

	@ApiModelProperty(value = "User's username", position = 3, example = "john.doe")
	private String username;
	
	@ApiModelProperty(value = "User's email", position = 4, example = "john.doe@gmail.com")
	private String email;

	@ApiModelProperty(value = "verified", position = 5, example = "false")
	private Boolean verified;

	@ApiModelProperty(value = "User's roles", position = 6, dataType = "List")
	private List<RoleDTO> roles;

	public UserDTO(User entity) {

		this.id = entity.getId();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.username = entity.getUsername();
		this.email = entity.getEmail();
		this.roles = entity.getRoles().stream().map(RoleDTO::new).collect(toList());
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
}