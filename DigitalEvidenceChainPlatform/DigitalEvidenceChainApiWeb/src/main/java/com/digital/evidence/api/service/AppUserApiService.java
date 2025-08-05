package com.digital.evidence.api.service;

import java.util.List;
import java.util.Optional;

import com.digital.evidence.api.dto.AppUserDTO;

public interface AppUserApiService {
	List<AppUserDTO> findAll();
    Optional<AppUserDTO> findById(Long id);
    AppUserDTO create(AppUserDTO dto);
    Optional<AppUserDTO> update(Long id, AppUserDTO dto);
    void delete(Long id);
    List<AppUserDTO> filterUsers(String username, String role);
}
