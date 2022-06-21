package com.infotrends.in.InfoTrendsIn.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.infotrends.in.InfoTrendsIn.data.AddressDetails;
import com.infotrends.in.InfoTrendsIn.data.Users;
import com.infotrends.in.InfoTrendsIn.exceptions.AddressFieldExceptions;
import com.infotrends.in.InfoTrendsIn.exceptions.UserExceptions;
import com.infotrends.in.InfoTrendsIn.exceptions.config.ErrorsMappings;
import com.infotrends.in.InfoTrendsIn.model.AddressRequestModel;
import com.infotrends.in.InfoTrendsIn.model.AddressResponseModel;
import com.infotrends.in.InfoTrendsIn.model.UsersResponseModel;
import com.infotrends.in.InfoTrendsIn.resources.AddressResource;
import com.infotrends.in.InfoTrendsIn.resources.UserResources;
import com.infotrends.in.InfoTrendsIn.service.AddressServices;
import com.infotrends.in.InfoTrendsIn.service.UsersService;
import com.infotrends.in.InfoTrendsIn.utils.Utilities;

@Component
public class AddressOrc {

	@Autowired
	private AddressServices addressSvc;
	
	@Autowired
	private UsersService userSvc;
	
	public AddressResponseModel saveDetails(AddressRequestModel data) {
		AddressResponseModel resp = new AddressResponseModel();			
		AddressDetails details = new AddressDetails(data);
		Optional<Users> user = userSvc.findById(details.getUserId());
		if(!user.isPresent()) {
			throw new UserExceptions.UserNotFoudException(ErrorsMappings.USER_NOT_FOUND_MESSAGE);
		}
		details = addressSvc.save(details);
		resp.setCode(HttpStatus.CREATED.value());
		resp.setAddressId(resp.getAddressId());
		return resp;
			
	}

	
	public AddressResponseModel updateDetails(String addressId,AddressRequestModel data) throws AddressFieldExceptions.NotFoudException {
		AddressResponseModel resp = new AddressResponseModel();
		Optional<AddressDetails> optDetails = addressSvc.findByAddrId(addressId);

		if(optDetails.isPresent()) {
			AddressDetails details = optDetails.get();
			
			if(Utilities.validString(data.getHouseNo())) {
				details.setHouseNo(data.getHouseNo());
			}
			if(Utilities.validString(data.getAddressLine1())) {
				details.setAddressLine1(data.getAddressLine1());
			}
			if(Utilities.validString(data.getAddressLine2())) {
				details.setAddressLine2(data.getAddressLine2());
			}
			if(Utilities.validString(data.getLandmark())) {
				details.setLandmark(data.getLandmark());
			}
			if(Utilities.validString(data.getCity())) {
				details.setCity(data.getCity());
			}
			if(Utilities.validString(data.getState())) {
				details.setState(data.getState());
			}
			if(Utilities.validLong(data.getPincode())) {
				details.setPincode(data.getPincode());
			}
			if(Utilities.validString(data.getUserId())) {
			
				Optional<Users> user = userSvc.findById(data.getUserId());
				if(!user.isPresent()) {
					throw new UserExceptions.UserNotFoudException(ErrorsMappings.USER_NOT_FOUND_MESSAGE);
				}
				details.setUserId(data.getUserId());
			}
			if(Utilities.validString(data.getAdditionalInfo())) {
				details.setAdditionalInfo(data.getAdditionalInfo());
			}
//			details.setAddressDetails(data);
			details = addressSvc.save(details);
			resp.setCode(HttpStatus.OK.value());
			resp.setAddressId(resp.getAddressId());
			return resp;
		} else {
			throw new AddressFieldExceptions.NotFoudException(ErrorsMappings.ADDRESS_NOT_FOUND_MESSAGE);
		}
	}
	
	public AddressResponseModel deleteDetails(String addressId) throws AddressFieldExceptions.NotFoudException {
		AddressResponseModel resp = new AddressResponseModel();
		boolean isDeleted = addressSvc.deleteAddress(addressId);
		if(isDeleted) {
			resp.setCode(HttpStatus.OK.value());
			return resp;
		} else {
			throw new AddressFieldExceptions.NotFoudException(ErrorsMappings.ADDRESS_NOT_FOUND_MESSAGE);
		}
		
	}
	
	public AddressDetails fetchAddressDetails(String addressId) throws AddressFieldExceptions.NotFoudException {
		Optional<AddressDetails> details = addressSvc.findByAddrId(addressId);
		if(details.isPresent()) {
			return details.get();
		}
		throw new AddressFieldExceptions.NotFoudException(ErrorsMappings.ADDRESS_NOT_FOUND_MESSAGE);
	}

	public List<AddressDetails> fetchAddressDetailsByCustId(String custId) {
		List<AddressDetails> details = addressSvc.findByCustId(custId);
		return details;
	}
	
//	private boolean isDataValid(AddressRequestModel data) throws Exception {
//		
//		if(data.getAddressLine1()!=null && !data.getAddressLine1().equalsIgnoreCase("") && data.getAddressLine2()!=null 
//				&& !data.getAddressLine2().equalsIgnoreCase("") && data.getCity()!=null && !data.getCity().equalsIgnoreCase("")
//				&& data.getHouseNo()!=null && !data.getHouseNo().equalsIgnoreCase("") && data.getLandmark()!=null 
//				&& !data.getLandmark().equalsIgnoreCase("") && data.getState()!=null && !data.getState().equalsIgnoreCase("")
//				&& Long.compare(data.getPincode(),99999)>0 && Long.compare(data.getPincode(),1000000)<0) {
//			return true;
//		}
//		
//		return false;
//	}
	

	
	public EntityModel<AddressResponseModel> generateHateoas(EntityModel<AddressResponseModel> entityModel, String type, String id) {
		Map<String, WebMvcLinkBuilder> buildersMap = new HashMap<>();
		if(!type.equalsIgnoreCase("create")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressResource.class).createAddressDetails(null)); 
			buildersMap.put("create-addr", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("update-addr")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressResource.class).findAddressDetailsById(id)); 
			buildersMap.put("view-addr", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("view-addr")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressResource.class).updateAddressDetails(id, null)); 
			buildersMap.put("update-addr", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("view-addr") || type.equalsIgnoreCase("update-addr")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressResource.class).deleteAddressDetails(id)); 
			buildersMap.put("delete-addr", link);
		}
		if(type.equalsIgnoreCase("create") || type.equalsIgnoreCase("view-addr") || type.equalsIgnoreCase("update-addr")) {
			WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressResource.class).findAddressDetailsByCustID(id)); 
			buildersMap.put("view-cust-addr", link);
		}
		
		entityModel = (EntityModel<AddressResponseModel>) Utilities.addLinksToEntity(entityModel, buildersMap);
		return entityModel;
	}
	
}
