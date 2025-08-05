package com.digital.evidence.api.dto;

import java.util.Set;

public class AppUserDTO {
	private Long id;
	private String username;
	private String password;
	private String fullName;
	private Set<String> roles;
	private boolean enabled;

	public AppUserDTO() {
	}

	public AppUserDTO(Long id, String username, String fullName, boolean enabled,String password, Set<String> roles) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.enabled = enabled;
		this.password = password;
	    this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
