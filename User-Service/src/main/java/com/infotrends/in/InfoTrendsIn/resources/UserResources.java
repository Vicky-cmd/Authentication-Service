package com.infotrends.in.InfoTrendsIn.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.infotrends.in.InfoTrendsIn.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infotrends.in.InfoTrendsIn.business.UsersProcesses;
import com.infotrends.in.InfoTrendsIn.data.Users;
import com.infotrends.in.InfoTrendsIn.exceptions.UserExceptions;
import com.infotrends.in.InfoTrendsIn.exceptions.config.ErrorsMappings;
import com.infotrends.in.InfoTrendsIn.exceptions.validations.ValidationLevel;
import com.infotrends.in.InfoTrendsIn.model.UsersRequestModel;
import com.infotrends.in.InfoTrendsIn.model.UsersResponseModel;
import com.infotrends.in.InfoTrendsIn.security.SecurityConstants;
import com.infotrends.in.InfoTrendsIn.service.UsersService;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Loggable
@RestController
@RequestMapping("/api/v1/users")
public class UserResources {

	@Autowired
	private UsersProcesses usersProcess;
	
	@Autowired
	private UsersService usersSvc;

	@CircuitBreaker(name = "usersBreaker", fallbackMethod = "usersFallbackMethod")
	@PreAuthorize("hasAnyAuthority('USER_WRITE')")
	@PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<UsersResponseModel>> createUser(@Validated(ValidationLevel.onCreate.class) @RequestBody UsersRequestModel usersRequest) {
		UsersResponseModel respModel = usersProcess.createUser(usersRequest);
		EntityModel<UsersResponseModel> entity = EntityModel.of(respModel);
		entity = usersProcess.generateHateoas(entity, this, "create", respModel.getUserId());
		return new ResponseEntity(entity, HttpStatus.CREATED);
	}

	@CircuitBreaker(name = "usersBreaker", fallbackMethod = "usersFallbackMethod")
	@GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<UsersResponseModel>> getUsers() {
		UsersResponseModel respModel = new UsersResponseModel();
		List<Users> usersLst = usersSvc.getUsersList();
		respModel.setUsersLst(usersLst);
		respModel.setCode(HttpStatus.OK.value());
		
		EntityModel<UsersResponseModel> entity = EntityModel.of(respModel);
		entity = usersProcess.generateHateoas(entity, this, "all-users", null);
		return new ResponseEntity(entity, HttpStatus.OK);
	}
	
	@CircuitBreaker(name = "usersBreaker", fallbackMethod = "usersFallbackMethod")
	@PreAuthorize("hasAnyAuthority('USER_READ', 'USER')")
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<UsersResponseModel>> getUserById(@PathVariable("id") String id) {
		UsersResponseModel respModel = new UsersResponseModel();
		Optional<Users> user = usersSvc.findById(id);
		if(!user.isPresent()) {
			throw new UserExceptions.UserNotFoudException(ErrorsMappings.USER_NOT_FOUND_MESSAGE);
		}
		respModel.setUser(user.get());
		respModel.setCode(HttpStatus.OK.value());
		
		EntityModel<UsersResponseModel> entity = EntityModel.of(respModel);
		entity = usersProcess.generateHateoas(entity, this, "view-user", user.get().getId());
		return new ResponseEntity(entity, HttpStatus.OK);
	}
	

	@CircuitBreaker(name = "usersBreaker", fallbackMethod = "usersFallbackMethod")
	@PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<UsersResponseModel>> updateUserById(@PathVariable("id") String id, 
			@Validated(ValidationLevel.onUpdate.class) @RequestBody UsersRequestModel usersRequest) {
		UsersResponseModel respModel = new UsersResponseModel();
		Users user = usersProcess.updateDetails(id, usersRequest);
		
		EntityModel<UsersResponseModel> entity = EntityModel.of(respModel);
		entity = usersProcess.generateHateoas(entity, this, "update-user", user.getId());
		return new ResponseEntity(entity, HttpStatus.OK);
	}
	
	@CircuitBreaker(name = "usersBreaker", fallbackMethod = "usersFallbackMethod")
	@PutMapping(value = "/{id}/updatePassword", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<UsersResponseModel>> updatePassword(@PathVariable("id") String id, 
			@Validated(ValidationLevel.onPasswordChange.class) @RequestBody UsersRequestModel usersRequest) {
		UsersResponseModel respModel = new UsersResponseModel();
		Users user = usersProcess.updatePasswordDetails(id, usersRequest);
		
		EntityModel<UsersResponseModel> entity = EntityModel.of(respModel);
		entity = usersProcess.generateHateoas(entity, this, "update-password", user.getId());
		return new ResponseEntity(entity, HttpStatus.OK);
	}
	
	@CircuitBreaker(name = "usersBreaker", fallbackMethod = "usersFallbackMethod")
	@DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<UsersResponseModel>> deleteUserById(@PathVariable("id") String id) {
		UsersResponseModel respModel = new UsersResponseModel();
		Optional<Users> user = usersSvc.findById(id);
		if(!user.isPresent()) {
			throw new UserExceptions.UserNotFoudException(ErrorsMappings.USER_NOT_FOUND_MESSAGE);
		}
		Users userData = user.get();
		userData.setDeletedFlag("Y");
		usersSvc.deleteEntry(userData);
		respModel.setCode(HttpStatus.OK.value());
		
		EntityModel<UsersResponseModel> entity = EntityModel.of(respModel);
		entity = usersProcess.generateHateoas(entity, this, "delete-user", null);
		return new ResponseEntity(entity, HttpStatus.OK);
	}

	
	@CircuitBreaker(name = "usersBreaker", fallbackMethod = "usersFallbackMethod")
	@PostMapping(value = "/authenticate")
	public ResponseEntity<EntityModel<UsersResponseModel>> authenticateUser(@Validated(ValidationLevel.onAuthenticateUser.class) @RequestBody UsersRequestModel usersRequest) {
		UsersResponseModel respModel = new UsersResponseModel();
		Users user = usersProcess.validateUserLogin(usersRequest);
		respModel.setUser(user);
		respModel.setCode(HttpStatus.OK.value());
		EntityModel<UsersResponseModel> entity = EntityModel.of(respModel);
//		entity = usersProcess.generateHateoas(entity, this, "authenticate", user.getId());
		String token = usersProcess.generateToken(user);
		return ResponseEntity.ok().header(SecurityConstants.HEADER, token).body(entity);		
	}


	public ResponseEntity<UsersResponseModel> usersFallbackMethod(CallNotPermittedException ex) {
		UsersResponseModel respModel = new UsersResponseModel();
		respModel.setCode(HttpStatus.BAD_GATEWAY.value());
		respModel.setMessage(ex.getMessage());
		return  new ResponseEntity(respModel, HttpStatus.BAD_GATEWAY);
	}
}
