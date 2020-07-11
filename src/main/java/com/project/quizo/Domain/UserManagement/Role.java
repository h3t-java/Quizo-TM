package com.project.quizo.Domain.UserManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "role", schema = "public")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
	@SequenceGenerator(name = "role_id_generator", sequenceName = "role_id_seq", allocationSize = 1)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String name;

	@ManyToMany(mappedBy = "roles")
	@JsonBackReference
	private List<User> users = new ArrayList<>();

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUsers(Collection<User> users) {
		this.users.clear();
		this.users.addAll(users);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}