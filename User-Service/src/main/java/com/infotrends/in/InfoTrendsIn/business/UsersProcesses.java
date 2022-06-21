package com.infotrends.in.InfoTrendsIn.business;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.infotrends.in.InfoTrendsIn.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.infotrends.in.InfoTrendsIn.data.AddressDetails;
import com.infotrends.in.InfoTrendsIn.data.Users;
import com.infotrends.in.InfoTrendsIn.exceptions.UserExceptions;
import com.infotrends.in.InfoTrendsIn.exceptions.config.ErrorsMappings;
import com.infotrends.in.InfoTrendsIn.model.UsersRequestModel;
import com.infotrends.in.InfoTrendsIn.model.UsersResponseModel;
import com.infotrends.in.InfoTrendsIn.resources.AddressResource;
import com.infotrends.in.InfoTrendsIn.resources.UserResources;
import com.infotrends.in.InfoTrendsIn.security.SecurityConstants;
import com.infotrends.in.InfoTrendsIn.service.UsersService;
import com.infotrends.in.InfoTrendsIn.utils.Utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class UsersProcesses {

	@Autowired
	private UsersService userSvc;
	
	@Autowired
	private AddressOrc addrOrc;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public UsersResponseModel createUser(UsersRequestModel usersRequest) throws UserExceptions.UserDataException {
		
		UsersResponseModel respModel = new UsersResponseModel();
			
		Optional<Users> existingUser = userSvc.getByUsrName(usersRequest.getUsername());
		
		if(existingUser.isPresent()) {
			throw new UserExceptions.UserDataException(ErrorsMappings.USER_EXISTS_MESSAGE);
		}
		
		Users user = new Users(usersRequest);
		user.setCreatedOn(new Date());
		user.setDeletedFlag("N");
		user.setIsAdmin((usersRequest.getIsAdmin()!=null && !usersRequest.getIsAdmin().isEmpty())?usersRequest.getIsAdmin():"N");
		userSvc.encryptAndSave(user);
		respModel.setCode(HttpStatus.CREATED.value());
		respModel.setMessage("User Created Successfully!");
		respModel.setUserId(user.getId());
	
		return respModel;
	}

	
	public EntityModel<UsersResponseModel> generateHateoas(EntityModel<UsersResponseModel> entityModel, UserResources userResources, String type, String id) {
		Map<String, WebMvcLinkBuilder> buildersMap = new HashMap<>();
		if(!type.equalsIgnoreCase("create")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userResources.getClass()).createUser(null)); 
			buildersMap.put("create-user", link);
		}
		if(!type.equalsIgnoreCase("all-users")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userResources.getClass()).getUsers()); 
			buildersMap.put("all-users", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("update-user") || type.equalsIgnoreCase("update-password")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userResources.getClass()).getUserById(id)); 
			buildersMap.put("view-user", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("view-user") || type.equalsIgnoreCase("update-password")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userResources.getClass()).updateUserById(id, null)); 
			buildersMap.put("update-user", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("view-user") || type.equalsIgnoreCase("update-user") || type.equalsIgnoreCase("update-password")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userResources.getClass()).deleteUserById(id)); 
			buildersMap.put("delete-user", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("view-user") || type.equalsIgnoreCase("update-user")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userResources.getClass()).updatePassword(id, null)); 
			buildersMap.put("update-password", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("view-user") || type.equalsIgnoreCase("update-user")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressResource.class).findAddressDetailsByCustID(id)); 
			buildersMap.put("view-cust-addr", link);
		}
		
		entityModel = (EntityModel<UsersResponseModel>) Utilities.addLinksToEntity(entityModel, buildersMap);
		return entityModel;
	}


	public Users updateDetails(String id, UsersRequestModel usersRequest) throws UserExceptions.UserNotFoudException {
		// TODO Auto-generated method stub
		Optional<Users> user = userSvc.findById(id);
		if(!user.isPresent()) {
			throw new UserExceptions.UserNotFoudException(ErrorsMappings.USER_NOT_FOUND_MESSAGE);
		}
		Users userData = user.get();
		
		if(Utilities.validString(usersRequest.getFullname())) {
			userData.setFullname(usersRequest.getFullname());
		}
		if(Utilities.validString(usersRequest.getIsAdmin())) {
			userData.setIsAdmin(usersRequest.getIsAdmin());
		}
		
		if(Utilities.validString(usersRequest.getIsSeller())) {
			
			if(usersRequest.getIsSeller().equals("Yes")) {
				userData.setSeller(true);
				if(Utilities.validString(usersRequest.getSellerAddressID())) {
					AddressDetails addr = addrOrc.fetchAddressDetails(usersRequest.getSellerAddressID());
					if(addr!=null) {
						userData.setSellerAddress(addr.getId());
					}
					if(Utilities.validString(usersRequest.getSellerGSTN())) {
						userData.setSellerGSTN(usersRequest.getSellerGSTN());
					}
					if(Utilities.validString(usersRequest.getSellerBio())) {
						userData.setSellerBio(usersRequest.getSellerBio());
					}
				} else {
					throw new UserExceptions.RequestExpectationFailedException("Seller Address is Mandatory!");
				}
			} else {
				userData.setSeller(false);
			}
			
		}
		
		userData = userSvc.saveOrUpdate(userData);
		
		return userData;
	}


	public Users updatePasswordDetails(String id, UsersRequestModel usersRequest) throws UserExceptions.UserNotFoudException {
		// TODO Auto-generated method stub
		Optional<Users> user = userSvc.findById(id);
		if(!user.isPresent()) {
			throw new UserExceptions.UserNotFoudException(ErrorsMappings.USER_NOT_FOUND_MESSAGE);
		}
		Users userData = user.get();
		
		if(!encoder.matches(userData.getPassword(), usersRequest.getPassword())) {
			throw new UserExceptions.InvalidCredentialsException(ErrorsMappings.INVALID_CREDENTIALS_MESSAGE);
		}
		
		if(usersRequest.getPassword().equals(usersRequest.getNew_password())) {
			throw new UserExceptions.PasswordExpectationFailedException(ErrorsMappings.UNCHANGED_NEW_PWD_MESSAGE);
		}
		
		userData.setPassword(usersRequest.getNew_password());
		userData = userSvc.encryptAndUpdate(userData);
		
		return userData;
	}


	public Users validateUserLogin(UsersRequestModel usersRequest) {
		Optional<Users> user = userSvc.getByUsrName(usersRequest.getUsername());
		if(!user.isPresent()) {
			throw new UserExceptions.UserNotFoudException(ErrorsMappings.USER_NOT_FOUND_MESSAGE);
		}
		
		Users userData = user.get();
		if(!encoder.matches(usersRequest.getPassword(), userData.getPassword())) {
			throw new UserExceptions.InvalidCredentialsException(ErrorsMappings.INVALID_CREDENTIALS_MESSAGE);
		}
		
		return userData;
	}


	public String generateToken(Users user) {
		// TODO Auto-generated method stub
		Date expDate = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
		Claims claims = Jwts.claims().setSubject(user.getId());
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date())
					.setExpiration(expDate).signWith(SignatureAlgorithm.HS512, SecurityConstants.KEY)
					.compact();
				
	}
	
}
