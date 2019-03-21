package br.qxd.jh.registry.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.qxd.jh.registry.dto.ApiResponseDTO;
import br.qxd.jh.registry.dto.DeleteUserRequestDTO;
import br.qxd.jh.registry.dto.UserDTO;
import br.qxd.jh.registry.model.Role;
import br.qxd.jh.registry.model.RoleName;
import br.qxd.jh.registry.model.User;
import br.qxd.jh.registry.repository.RoleRepository;
import br.qxd.jh.registry.service.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UserService userService;
	
	@Bean
	private BCryptPasswordEncoder bCryptEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping()
	public ResponseEntity<ApiResponseDTO> register(@Valid @RequestBody UserDTO user) {
		
		//Verifica se já existe usuário cadastrado com o username informado
		if (userService.existsByUsername(user.getUsername())) {
			return new ResponseEntity<>(new ApiResponseDTO(false, "Username already in use."), HttpStatus.BAD_REQUEST);
		}
		
		//Criando a conta do usuário
		
		User us = new User(user.getUsername(), bCryptEncoder().encode(user.getPassword()), user.getName());
		
		Role usRole = roleRepo.findByName(RoleName.ROLE_USER).orElse(new Role(RoleName.ROLE_USER));
		
		us.setRoles(Collections.singleton(usRole));
		
		userService.saveUser(us);
		
		return ResponseEntity.ok(new ApiResponseDTO(true, "User created successfully."));
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/delete")
	public ResponseEntity<ApiResponseDTO> deleteUser(@Valid @RequestBody DeleteUserRequestDTO req) {
		//Verifica se já existe usuário cadastrado com o username informado
		if (!userService.existsByUsername(req.getUsername())) {
			return new ResponseEntity<>(new ApiResponseDTO(false, "Username doesn't exists."), HttpStatus.BAD_REQUEST);
		}
		
		userService.deleteByUsername(req.getUsername());
		
		return ResponseEntity.ok(new ApiResponseDTO(true, "User deleted successfully."));
	}
	
	@GetMapping()
	public List<User> listAllUsers() {
		return (List<User>) userService.findAll();
	}
}
