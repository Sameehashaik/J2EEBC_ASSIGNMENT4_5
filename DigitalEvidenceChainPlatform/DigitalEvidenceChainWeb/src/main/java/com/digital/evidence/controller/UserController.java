package com.digital.evidence.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.digital.evidence.entity.AppUser;
import com.digital.evidence.service.AppUserService;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private AppUserService userService;

	@GetMapping
	public String listUsers(@RequestParam(required = false) String username,
	                        @RequestParam(required = false) String role,
	                        Model model) {
	    List<AppUser> users = userService.filterUsers(username, role);
	    model.addAttribute("users", users);
	    model.addAttribute("username", username);
	    model.addAttribute("role", role);
	    return "user/list";
	}
	
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("user", new AppUser());
		return "user/create";
	}

	@PostMapping
	public String createUser(@ModelAttribute("user") AppUser user) {
		userService.create(user);
		return "redirect:/users";
	}

	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model) {
		Optional<AppUser> userOptional = userService.findById(id);
	    if (userOptional.isPresent()) {
	        AppUser user = userOptional.get();
	        model.addAttribute("user", user);

	        List<String> allRoles = List.of("USER", "ADMIN", "SUPPORT"); 
	        model.addAttribute("allRoles", allRoles);

	        return "user/edit";
	    } else {
	        return "redirect:/users";
	    }
	}

	@PostMapping("/{id}")
	public String updateUser(@PathVariable Long id, @ModelAttribute("user") AppUser user) {
		user.setId(id);
		userService.update(id, user);
		return "redirect:/users";
	}

	@GetMapping("/{id}")
	public String viewUser(@PathVariable Long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "user/view";
	}

	@PostMapping("/{id}/delete")
	public String deleteUser(@PathVariable Long id) {
		userService.delete(id);
		return "redirect:/users";
	}
}
