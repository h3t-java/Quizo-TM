package com.project.quizo.resource;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.UniqueElements;

import io.swagger.annotations.ApiModelProperty;

public class CreateUserDTO extends UserRegisterDTO {
	
	@NotEmpty
	@UniqueElements
	@ApiModelProperty(position = 5, required = true, allowableValues = "Positive integer", dataType = "List", example = "[2]")
	private List<@Positive Long> rolesIds;
	
	public List<Long> getRolesIds() {
		return rolesIds;
	}

	public void setRolesIds(List<Long> rolesIds) {
		this.rolesIds = rolesIds;
	}

}