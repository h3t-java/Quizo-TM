package com.project.quizo.Resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserChangePassDTO {

	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(position = 1, required = true, allowableValues = "Not blank string up to 50 characters", example = "oldPassword")
	private String oldPassword;

	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(position = 2, required = true, allowableValues = "Not blank string up to 50 characters", example = "jU@dsrIzK2qD0")
	private String newPassword;

	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(position = 3, required = true, allowableValues = "Not blank string up to 50 characters", example = "john.doe")
	private String username;

	public UserChangePassDTO() {
	}

	public UserChangePassDTO(String oldPassword, String newPassword, String username) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}