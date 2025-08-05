package com.digital.evidence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.evidence.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByUsernameContainingIgnoreCase(String username);
    List<AppUser> findByRolesContainingIgnoreCase(String role);
    List<AppUser> findByUsernameContainingIgnoreCaseAndRolesContainingIgnoreCase(String username, String role);
}
