package com.infotrends.in.InfoTrendsIn.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.infotrends.in.InfoTrendsIn.data.AddressDetails;
import com.infotrends.in.InfoTrendsIn.exceptions.validations.CustomMin;
import com.infotrends.in.InfoTrendsIn.exceptions.validations.ValidationLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AddressRequestModel {

	
	private int addressId;
	
	@NotNull(message = "Customer ID is Mandatory", groups = {ValidationLevel.onCreate.class})
	@Min(value = 9, message = "Customer ID must be atleast 9 characters long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String userId;

	@NotNull(message = "House Number is Mandatory", groups = {ValidationLevel.onCreate.class})
	@Min(value = 1,message = "House Number must be atleast 1 character long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String houseNo;
	
	@NotNull(message = "Address Line 1 is Mandatory", groups = {ValidationLevel.onCreate.class})
	@Min(value = 5, message = "Address Line 1 must be atlease 5 characters long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String addressLine1;
	
	@CustomMin(value = 5, message = "Address Line 2 must be atlease 5 characters long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String addressLine2;
	
	@CustomMin(value = 5, message = "Landmark must be atlease 5 characters long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private String landmark;
	
	@NotNull(message = "City is Mandatory", groups = {ValidationLevel.onCreate.class})
	private String city;
	
	@NotNull(message = "State is Mandatory", groups = {ValidationLevel.onCreate.class})
	private String state;
	
	@NotNull(message = "Pincode is Mandatory", groups = {ValidationLevel.onCreate.class})
	@Digits(integer = 6, fraction = 0, message = "pincode must 6 characters long", groups = {ValidationLevel.onCreate.class, ValidationLevel.onUpdate.class})
	private long pincode;

	private String additionalInfo;
	
}
