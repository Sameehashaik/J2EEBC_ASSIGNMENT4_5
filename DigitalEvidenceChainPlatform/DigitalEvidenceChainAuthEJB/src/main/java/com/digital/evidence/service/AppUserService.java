package com.digital.evidence.service;

import java.util.List;
import java.util.Optional;

import com.digital.evidence.entity.AppUser;

public interface AppUserService {
	List<AppUser> findAll();

	Optional<AppUser> findById(Long id);

	AppUser create(AppUser user);

	AppUser update(Long id, AppUser updatedUser);

	void delete(Long id);
	
	List<AppUser> filterUsers(String username, String role);
}
