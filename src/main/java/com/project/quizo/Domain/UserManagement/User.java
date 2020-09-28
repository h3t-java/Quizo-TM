package com.project.quizo.Domain.UserManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.quizo.Domain.TestManagement.Test;
import com.project.quizo.Domain.TestManagement.TestTake;
import com.project.quizo.Resource.UserRegisterDTO;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Column(name = "username", unique = true)
    @NotBlank
    @Size(max = 50)
    private String username;

    @Column(name = "email", unique = true)
    @Email
    @Size(max = 255)
    private String email;

    @NotBlank
    @Column(name = "password")
    @Size(max = 255)
    private String password;

    @Column(name = "verified")
    private Boolean verified = Boolean.FALSE;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id")})
    @JsonManagedReference
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<TestTake> testTakes;

    @OneToMany(mappedBy = "createdBy")
    private List<Test> tests;

    public User() {
    }

    public User(UserRegisterDTO userRegisterDTO, List<Role> userRoles) {
        this.firstName = userRegisterDTO.getFirstName();
        this.lastName = userRegisterDTO.getLastName();
        this.username = userRegisterDTO.getUsername();
        this.email = userRegisterDTO.getEmail();
        this.roles = userRoles;
        this.password = userRegisterDTO.getPassword();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    public List<TestTake> getTestTakes() {
        return testTakes;
    }

    public void setTestTakes(List<TestTake> testTakes) {
        this.testTakes = testTakes;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
