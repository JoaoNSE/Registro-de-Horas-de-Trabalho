package br.qxd.jh.registry.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.qxd.jh.registry.dto.UserDTO;
import br.qxd.jh.registry.model.User;
import br.qxd.jh.registry.repository.UserRepository;

@RequestMapping("/users")
@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping()
	public void addUserData(@Valid @RequestBody UserDTO user) {
		userRepo.save(new User(user.getUsername(), user.getPassword(), user.getName()));
	}
	
	@GetMapping()
	public List<User> listAllUsers() {
		return (List<User>) userRepo.findAll();
	}

}
