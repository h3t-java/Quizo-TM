package com.project.quizo.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserLoginPostDTO {

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(position = 1, required = true, allowableValues = "Not blank string up to 50 characters", example = "john.doe")
    private String username;

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(position = 2, required = true, allowableValues = "Not blank string up to 50 characters", example = "john23doe")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}