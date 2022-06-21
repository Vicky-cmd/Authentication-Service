package com.infotrends.in.InfoTrendsIn.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Field;

import com.infotrends.in.InfoTrendsIn.exceptions.validations.CustomMin;
import com.infotrends.in.InfoTrendsIn.exceptions.validations.ValidationLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersRequestModel {
	
	@NotNull(message="Name is mandatory", groups = {ValidationLevel.onCreate.class})
	@Size(min = 2, message = "Name must be atleast two characters long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String fullname;
	
	@NotNull(groups = {ValidationLevel.onCreate.class, ValidationLevel.onPasswordChange.class, ValidationLevel.onAuthenticateUser.class}, message = "Username is mandatory")
	@Pattern(regexp = "^(.+)@(.+).(.+)$", message = "Please Enter a Valid Email Id", groups = {ValidationLevel.onCreate.class, ValidationLevel.onPasswordChange.class, ValidationLevel.onAuthenticateUser.class})
	private String username;

	@NotNull(message = "Password is mandatory", groups = {ValidationLevel.onCreate.class, ValidationLevel.onPasswordChange.class, ValidationLevel.onAuthenticateUser.class})
	@Size(min = 8, message = "Password must be atleast 8 characters long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onPasswordChange.class, ValidationLevel.onAuthenticateUser.class})
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{2,}$", message = "Password must contain atleast One Special Character and a Capital Letter", groups = {ValidationLevel.onCreate.class, ValidationLevel.onPasswordChange.class, ValidationLevel.onAuthenticateUser.class})
	private String password;
	
	@NotNull(message = "Password is mandatory", groups = {ValidationLevel.onPasswordChange.class})
	@Size(min = 8, message = "Password must be atleast 8 characters long", groups = {ValidationLevel.onPasswordChange.class})
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{2,}$", message = "Password must contain atleast One Special Character and a Capital Letter", groups = {ValidationLevel.onPasswordChange.class})
	private String new_password;
	
	@NotNull(message = "" , groups = {ValidationLevel.onCreate.class})
	@Pattern(regexp = "^(?:Y|N)$", message = "Flag must be in Y/N format", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String isAdmin = "N";	

//	@Pattern(regexp = "^([789]\\d{9}$", message = "Please Enter A Valid Mobile Number", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String phoneNo;
	
	@Pattern(regexp = "^(?:Yes|No)$", message = "Only Yes/No values accepted", groups = {ValidationLevel.onUpdate.class})
	private String isSeller;
	
	@Size(min = 2, message = "Sales Group ID must be atleast two characters long", groups = {ValidationLevel.onUpdate.class})
	private String sellerAddressID;
	
	@Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Please Enter a valid GSTIN", groups = {ValidationLevel.onUpdate.class})
	private String sellerGSTN;
	
	private String sellerBio;
}
