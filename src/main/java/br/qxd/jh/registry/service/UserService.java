package br.qxd.jh.registry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.qxd.jh.registry.model.User;
import br.qxd.jh.registry.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public void saveUser(User user) {
		userRepo.save(user);
	}
}
