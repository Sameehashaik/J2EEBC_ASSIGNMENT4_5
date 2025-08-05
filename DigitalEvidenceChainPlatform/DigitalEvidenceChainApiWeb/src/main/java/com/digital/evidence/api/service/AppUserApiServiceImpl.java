package com.digital.evidence.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.evidence.api.dto.AppUserDTO;
import com.digital.evidence.entity.AppUser;
import com.digital.evidence.service.AppUserService;

@Service
public class AppUserApiServiceImpl implements AppUserApiService {

    @Autowired
    private AppUserService appUserService;

    @Override
    public List<AppUserDTO> findAll() {
        return appUserService.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<AppUserDTO> findById(Long id) {
        return appUserService.findById(id).map(this::convertToDTO);
    }

    @Override
    public AppUserDTO create(AppUserDTO dto) {
        AppUser user = convertToEntity(dto);
        return convertToDTO(appUserService.create(user));
    }

    @Override
    public Optional<AppUserDTO> update(Long id, AppUserDTO dto) {
        AppUser updatedUser = convertToEntity(dto);
        return Optional.ofNullable(appUserService.update(id, updatedUser)).map(this::convertToDTO);
    }

    @Override
    public void delete(Long id) {
        appUserService.delete(id);
    }

    @Override
    public List<AppUserDTO> filterUsers(String username, String role) {
        return appUserService.filterUsers(username, role).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private AppUserDTO convertToDTO(AppUser user) {
        // If roles are Set<String>, just use as is
        Set<String> roleNames = user.getRoles();
        return new AppUserDTO(
            user.getId(),
            user.getUsername(),
            user.getFullName(),
            user.isEnabled(),
            user.getPassword(),   // for testing, will show the hash
            roleNames
        );
    }

    private AppUser convertToEntity(AppUserDTO dto) {
        AppUser user = new AppUser();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setEnabled(dto.isEnabled());
        user.setPassword(dto.getPassword()); // for testing, to show hash
        user.setRoles(dto.getRoles());
        return user;
    }
}
