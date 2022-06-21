package com.infotrends.in.authenticationserver.dao;

import com.infotrends.in.authenticationserver.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {

	Optional<Users> findByUsername(String username);
	
	Users findByUsernameAndPassword(String username, String password);
	
	HashSet<Users> findByUsernameContaining(String username); 
	
	HashSet<Users> findByFullnameContaining(String fullname); 
	
}
