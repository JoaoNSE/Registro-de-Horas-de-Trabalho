package br.qxd.jh.registry.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import br.qxd.jh.registry.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	public Optional<User> findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	@Transactional
	public void deleteByUsername(String username);
	
}
