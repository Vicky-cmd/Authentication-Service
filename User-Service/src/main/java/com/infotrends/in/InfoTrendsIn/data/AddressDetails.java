package com.infotrends.in.InfoTrendsIn.data;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.infotrends.in.InfoTrendsIn.model.AddressRequestModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
public class AddressDetails {

	@Id
	@Field(name = "_id")
	private String id;
	
	@NotNull
	private String userId;
	
	@NotNull
	private String houseNo;
	
	@NotNull
	private String addressLine1;
	
	private String addressLine2;
	
	private String landmark;

	@NotNull
	private String city;

	@NotNull
	private String state;

	@NotNull
	private long pincode;
	
	private String additionalInfo;

	
	@NotNull
	@Field(name = "Created_By")
	protected String createdBy;
	
	@NotNull
	@Field(name = "Modified_By")
	protected String modifiedBy;
	
	@NotNull
	@Field(name = "Created_On")
	protected Date createdOn;
	
	@NotNull
	@Field(name = "Modified_On")
	protected Date modifiedOn;
	

	public AddressDetails (AddressRequestModel details) {
		super();
		this.userId = details.getUserId();
		this.houseNo = details.getHouseNo();
		this.addressLine1 = details.getAddressLine1();
		this.addressLine2 = details.getAddressLine2();
		this.landmark = details.getLandmark();
		this.city = details.getCity();
		this.state = details.getState();
		this.pincode = details.getPincode();
		this.additionalInfo = details.getAdditionalInfo();
	}
	
}