package com.infotrends.in.authenticationserver.services;

import com.infotrends.in.authenticationserver.dao.UsersRepository;
import com.infotrends.in.authenticationserver.model.Users;
import com.infotrends.in.authenticationserver.utils.AppConstants;
import com.infotrends.in.authenticationserver.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	private SequenceGeneratorService sequenceGenSvc;
	
	public List<Users> getUsersList() {
		List<Users> usersLst = new ArrayList<Users>();
		usersRepository.findAll().forEach(user -> usersLst.add(user));
		return usersLst;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public Users encryptAndSave(Users user)   
	{  
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(AppConstants.bCryptEncoderStrength, new SecureRandom());
		String secPwd = encoder.encode(user.getPassword());
		user.setPassword(secPwd);
		user.setId(Utilities.generateId(AppConstants.USERID_PREFIX, AppConstants.USERID_FORMATTOR_PARAM, sequenceGenSvc.generateSequence("Users")));
		return saveOrUpdate(user);  
	}  
	
	public Users encryptAndUpdate(Users user)   
	{  
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(AppConstants.bCryptEncoderStrength, new SecureRandom());
		String secPwd = encoder.encode(user.getPassword());
		user.setPassword(secPwd);
		user.setId(Utilities.generateId(AppConstants.USERID_PREFIX, AppConstants.USERID_FORMATTOR_PARAM, sequenceGenSvc.generateSequence("Users")));
		return saveOrUpdate(user);  
	} 
	
	@Caching(evict = {@CacheEvict(value="user", key = "#user.getId()", allEntries = true)}, put = {@CachePut(value = "user", key = "#user.getId()", unless="#result == null")})
	public Users saveOrUpdate(Users user)   
	{  
		return usersRepository.save(user);  
	}  

	@CacheEvict(value="user", key = "#user.getId()", allEntries = true)
	public void deleteEntry(Users user) {
		usersRepository.delete(user);
	}
	
	public Users getUserByUsrNameAndPwd(Users user) {
		return usersRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
	public Optional<Users> getByUsrName(String userName) {
		return usersRepository.findByUsername(userName);
	}
	
	@Cacheable(value="user", key = "#id", unless="#result == null")
	public Optional<Users> findById(String id) {
		return usersRepository.findById(id);
	}
	
	public List<Users> findUsersByStr(String searchStr) {
		List<Users> result = new ArrayList<Users>();

		HashSet<Users> tmpresult = new HashSet<Users>();
		
		HashSet<Users> artName = usersRepository.findByUsernameContaining(searchStr);
		HashSet<Users> artPreview = usersRepository.findByFullnameContaining(searchStr);
		
		if(artName!=null && artName.size()>0) {
			tmpresult.addAll(artName);
		}
		if(artPreview!=null && artPreview.size()>0) {
			tmpresult.addAll(artPreview);
		}
		
		result = new ArrayList<Users>(tmpresult);
		System.out.println(result.size());
		
		return result;
	}
	
}
