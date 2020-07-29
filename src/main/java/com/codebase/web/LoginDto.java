package com.codebase.web;

import javax.validation.constraints.NotBlank;

public class LoginDto {
	@NotBlank
	private String	username;

	@NotBlank
	private String	password;

	private String	firstName;

	private String	lastName;

	private String	role;

	/**
	 * Default constructor
	 */
	protected LoginDto() {
	}

	/**
	 * Partial constructor
	 * 
	 * @param username
	 * @param password
	 */
	public LoginDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Full constructor
	 * 
	 * @param username
	 * @param password
	 */
	public LoginDto(String username, String password, String firstName, String lastName, String role) {
		this(username, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
