package br.qxd.jh.registry.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.qxd.jh.registry.model.Role;
import br.qxd.jh.registry.model.RoleName;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

	public Optional<Role> findByName(RoleName name);
	
}
