package com.project.quizo.Resource;

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