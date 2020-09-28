package com.project.quizo.resource;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class TestGenDTO {

    @NotBlank
    @ApiModelProperty(position = 0, required = true, allowableValues = "Not blank string", example = "Test Test ...")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
