package br.qxd.jh.registry.repository;

import org.springframework.data.repository.CrudRepository;

import br.qxd.jh.registry.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
