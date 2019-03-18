package br.qxd.jh.registry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.qxd.jh.registry.entity.User;
import br.qxd.jh.registry.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/add")
	public User addUser() {
		User user = new User("caberito", "hoje", "João das Neves");
		userRepo.save(user);
		
		return user;
	}
	
	@PostMapping("/add")
	
	public String addUserData(@RequestBody User user) {
		userRepo.save(user);
		return "deu bom";
	}
	
	@GetMapping("/get")
	public List<User> getUsers() {
		return (List<User>) userRepo.findAll();
	}

}
