package com.infotrends.in.InfoTrendsIn.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infotrends.in.InfoTrendsIn.model.UsersRequestModel;
import com.infotrends.in.InfoTrendsIn.utils.CustomSequenceGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection="users")
@Data
@NoArgsConstructor
public class Users implements Serializable {
	
	@Id
////	@GeneratedValue(generator = "sequence-generator")
////	@GenericGenerator(
////		name = "user-sequence-generator",
////	    strategy = "com.infotrends.in.InfoTrendsIn.config.CustomSequenceGenerator",
////	    parameters = {
////	    		@Parameter(name = CustomSequenceGenerator.INCREMENT_PARAM, value = "1"),
////	    		@Parameter(name = CustomSequenceGenerator.PREFIX_PARAM, value = "CID"),
////	    		@Parameter(name = CustomSequenceGenerator.NUMBER_FORMAT_PARAM, value = "%05d"),
////	    		@Parameter(name = CustomSequenceGenerator.INITIAL_PARAM, value = "1"),
////	    		@Parameter(name = CustomSequenceGenerator.GENERATOR_NAME, value = "user-sequence-generator")
////	    }
////	)
//	@Field("_id")
	private String id;
	
	@NotNull
	@Field(name = "Fullname")
	private String fullname;
	
	@NotNull
	@Field(name = "Username")
	private String username;
	
	@Field(name = "Password")
	@JsonIgnore
	private String password;

	@NotNull
	@Field(name = "isAdminUser")
	private String isAdmin;
	
	@NotNull
	@Field(name = "Timestamp")
	private Date timestamp = new Date();
	
	@NotNull
	@Field
	private Date createdOn;
	
	@NotNull
	@Field(name = "isOauthAccount")
	private String isOauthAccount = "N";

	@Field(name = "deletedFlag")
	private String deletedFlag;
	
	@Field(name = "isSeller")
	private boolean seller;
	
	@Field(name = "phoneNo")
	private long phoneNo;
	
	@Field(name = "sellerAddressID")
	private String sellerAddress;
	
	@Field(name = "sellerGSTN")
	private String sellerGSTN;

	@Field(name = "credentialsExpiryDate")
	private LocalDateTime credentialsExpiryDate;

	@Field(name = "isAccountExpired")
	private boolean isAccountExpired;

	@Field(name = "isAccountLocked")
	private boolean isAccountLocked;

	@Field(name = "sellerBio")
	private String sellerBio;

	public Users(UsersRequestModel userAuthModel) {
		fullname = userAuthModel.getFullname();
		username = userAuthModel.getUsername();
		password = userAuthModel.getPassword();
	}
	
	
	@Override
	public String toString() 
	{
		if(isAdmin!=null && isAdmin.equalsIgnoreCase("Y")) {
			return "User [id=" + id + ", uname=" + username + ", Admin User]";
		} else {
			return "User [id=" + id + ", uname=" + username + "]";
		}
	}
	
}
