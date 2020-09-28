package com.project.quizo.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class UserResetPassDTO {
	
	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(position = 0, required = true, allowableValues = "Not blank string up to 50 characters", example = "jU@dsrIzK2qD0")
	private String newPassword;

	@NotBlank
	@Size(max = 50)
	@ApiModelProperty(position = 1, required = true, allowableValues = "Not blank string up to 50 characters", example = "jU@dsrIzK2qD0")
	private String confirmPassword;

	@NotBlank
	@Size(max = 36)
	@ApiModelProperty(position = 2, required = true)
	private String token;
	
	@NotBlank
	@ApiModelProperty(position = 3, required = true)
	private String secret;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
}