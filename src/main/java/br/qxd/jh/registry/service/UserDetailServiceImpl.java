package br.qxd.jh.registry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.qxd.jh.registry.model.User;
import br.qxd.jh.registry.model.UserPrincipal;
import br.qxd.jh.registry.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	/**
	 * Carrega um usuário do banco de dados pelo nome.
	 * 
	 * @param username
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username)
		);
		
		return UserPrincipal.create(user);
	}
	
	/**
	 * Carrega um usuário do banco dedados pelo id.
	 * 
	 * @param id
	 * @return UserDetails
	 */
	public UserDetails findUserById(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
		return UserPrincipal.create(user);
	}

}
