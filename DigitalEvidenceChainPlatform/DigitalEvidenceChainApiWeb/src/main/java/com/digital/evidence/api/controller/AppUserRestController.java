package com.digital.evidence.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digital.evidence.api.dto.AppUserDTO;
import com.digital.evidence.api.service.AppUserApiService;

@RestController
@RequestMapping("/users")
public class AppUserRestController {
	@Autowired
	private AppUserApiService appUserApiService;

    @GetMapping("/all")
	public ResponseEntity<List<AppUserDTO>> listAll() {
		return ResponseEntity.ok(appUserApiService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AppUserDTO> getById(@PathVariable Long id) {
		return appUserApiService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/save")
	public ResponseEntity<AppUserDTO> create(@RequestBody AppUserDTO dto) {
		return ResponseEntity.status(201).body(appUserApiService.create(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AppUserDTO> update(@PathVariable Long id, @RequestBody AppUserDTO dto) {
		return appUserApiService.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		appUserApiService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/filter")
	public ResponseEntity<List<AppUserDTO>> filterUsers(@RequestParam(required = false) String username,
			@RequestParam(required = false) String role) {
		return ResponseEntity.ok(appUserApiService.filterUsers(username, role));
	}
}
