package com.project.quizo.resource;

public class LoginUserDTO {
	
	private long userId;
	
	public LoginUserDTO() {
	}

	public LoginUserDTO(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}