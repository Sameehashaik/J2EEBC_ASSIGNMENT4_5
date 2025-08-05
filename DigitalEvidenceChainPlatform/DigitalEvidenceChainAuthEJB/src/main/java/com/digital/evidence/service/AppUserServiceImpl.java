package com.digital.evidence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.digital.evidence.entity.AppUser;
import com.digital.evidence.repository.AppUserRepository;

import jakarta.transaction.Transactional;

@Service
public class AppUserServiceImpl implements AppUserService{

	private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public AppUser create(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppUser update(Long id, AppUser updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEnabled(updatedUser.isEnabled());
            user.setRoles(updatedUser.getRoles());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

	@Override
	public List<AppUser> filterUsers(String username, String role) {
	    if (username != null && !username.isEmpty() && role != null && !role.isEmpty()) {
	        return userRepository.findByUsernameContainingIgnoreCaseAndRolesContainingIgnoreCase(username, role);
	    } else if (username != null && !username.isEmpty()) {
	        return userRepository.findByUsernameContainingIgnoreCase(username);
	    } else if (role != null && !role.isEmpty()) {
	        return userRepository.findByRolesContainingIgnoreCase(role);
	    } else {
	        return findAll();
	    }
	}
}
