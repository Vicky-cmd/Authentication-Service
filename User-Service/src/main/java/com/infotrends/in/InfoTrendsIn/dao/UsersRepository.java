package com.infotrends.in.InfoTrendsIn.dao;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.infotrends.in.InfoTrendsIn.data.Users;

@Repository
public interface UsersRepository extends MongoRepository<Users, String>{

	Optional<Users> findByUsername(String username);
	
	Users findByUsernameAndPassword(String username, String password);
	
	HashSet<Users> findByUsernameContaining(String username); 
	
	HashSet<Users> findByFullnameContaining(String fullname); 
	
}
