package com.project.quizo.domain.verification;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.quizo.domain.userManagement.User;

@Entity
@Table(name = "verification_token", schema = "public")
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_tokens_id_generator")
	@SequenceGenerator(name = "verification_tokens_id_generator", sequenceName = "verification_token_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "token_value", unique = true)
	@NotNull
	private UUID value;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonManagedReference
	@NotNull
	private User user;
	
	@Column(name = "expiration_time")
	@NotNull
	@Future
	private LocalDateTime expirationTime;

	@Column(name = "expired")
	@NotNull
	private Boolean expired = Boolean.FALSE;

	public VerificationToken() {
	}


	public VerificationToken(User user, Long expirationTimeInMinutes) {
		this.user = user;
		value = UUID.randomUUID();
		expirationTime = LocalDateTime.now().plusMinutes(expirationTimeInMinutes);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getValue() {
		return value;
	}

	public void setValue(UUID value) {
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public VerificationToken resetExpirationTime(Long expirationTimeInMinutes) {
		expirationTime = LocalDateTime.now().plusMinutes(expirationTimeInMinutes);
		return this;
	}
}