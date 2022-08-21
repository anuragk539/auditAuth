package com.project.auditAuth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="users")
public class UserCredentials {

	@Id
	@NotBlank(message = "Username is mandatory")
	@Column
	String username;
	
	@NotNull
	@Size(min = 8)
	@NotBlank(message = "Password is mandatory")
	@Column
	String password;
}
