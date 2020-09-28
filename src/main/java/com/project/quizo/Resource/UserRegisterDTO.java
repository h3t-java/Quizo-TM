package com.project.quizo.Resource;

import com.project.quizo.config.FieldMatcher.FieldMatch;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.List;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
})
public class UserRegisterDTO {

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(position = 0, required = true, allowableValues = "Not blank string up to 50 characters", example = "John")
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(position = 1, required = true, allowableValues = "Not blank string up to 50 characters", example = "Doe")
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(position = 2, required = true, allowableValues = "Not blank string up to 50 characters", example = "john.doe")
    private String username;

    @Email
    @Size(max = 255)
    @ApiModelProperty(position = 3, required = true, allowableValues = "Valid email 255 characters", example = "john.doe@gmail.com")
    private String email;

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(position = 4, required = true, allowableValues = "Not blank string up to 50 characters", example = "pass1234")
    private String password;

    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(position = 5, required = true, allowableValues = "Not blank string up to 50 characters", example = "pass1234")
    private String confirmPassword;

	@AssertTrue
	private Boolean terms;

	// Hardcoded value for creating only users.
    @NotEmpty
    @UniqueElements
    @ApiModelProperty(position = 6, required = true, allowableValues = "Positive integer", dataType = "List", example = "[2]")
    private List<@Positive Long> rolesIds = Arrays.asList(2L);

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Long> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<Long> rolesIds) {
        this.rolesIds = rolesIds;
    }

	public Boolean getTerms() {
		return terms;
	}

	public void setTerms(Boolean terms) {
		this.terms = terms;
	}
}