package com.infotrends.in.authenticationserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


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

	@Field(name = "isReadOnlyUser")
	private boolean isReadOnlyUser;

	@Field(name = "sellerBio")
	private String sellerBio;


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
