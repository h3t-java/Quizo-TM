package com.project.quizo.Resource;

import com.project.quizo.Domain.UserManagement.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RoleDTO {
	
	@ApiModelProperty(value = "Role id", position = 1, example = "1")
	private Long id;
	
	@ApiModelProperty(value = "Role name", position = 2, example = "User_Manager")
	private String name;

	public RoleDTO(Role entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}