package com.infotrends.in.InfoTrendsIn.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infotrends.in.InfoTrendsIn.business.AddressOrc;
import com.infotrends.in.InfoTrendsIn.data.AddressDetails;
import com.infotrends.in.InfoTrendsIn.model.AddressRequestModel;
import com.infotrends.in.InfoTrendsIn.model.AddressResponseModel;
import com.infotrends.in.InfoTrendsIn.model.UsersResponseModel;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/api/v1/address")
@CrossOrigin(origins = "*")
public class AddressResource {

	
	@Autowired
	private AddressOrc addressOrc;
	
	@CircuitBreaker(name = "addrsBreaker", fallbackMethod = "usersFallbackMethod")
	@PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<AddressResponseModel>> createAddressDetails(@RequestBody AddressRequestModel addrModel) {
			
		AddressResponseModel respModel = new AddressResponseModel();	
		respModel = addressOrc.saveDetails(addrModel);
		EntityModel<AddressResponseModel> entity = EntityModel.of(respModel);
		entity = addressOrc.generateHateoas(entity, "create", respModel.getAddressId());
		return new ResponseEntity(entity, HttpStatus.CREATED);	
		
	}	

	
	@CircuitBreaker(name = "addrsBreaker", fallbackMethod = "usersFallbackMethod")
	@PutMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<AddressResponseModel>> updateAddressDetails( @RequestParam("addrId") String addrId,@RequestBody AddressRequestModel addrModel) {
			
		AddressResponseModel respModel = new AddressResponseModel();
		respModel = addressOrc.updateDetails(addrId, addrModel);
		respModel.setCode(HttpStatus.OK.value());
		EntityModel<AddressResponseModel> entity = EntityModel.of(respModel);
		entity = addressOrc.generateHateoas(entity, "update-addr", respModel.getAddressId());
		return new ResponseEntity(entity, HttpStatus.OK);	
		
	}		

	
	@CircuitBreaker(name = "addrsBreaker", fallbackMethod = "usersFallbackMethod")
	@GetMapping(value = "/{addrId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<AddressResponseModel>> findAddressDetailsById( @PathVariable("addrId") String addrId) {
			
		AddressResponseModel respModel = new AddressResponseModel();
		AddressDetails details = addressOrc.fetchAddressDetails(addrId);
		respModel.setAddress(details);
		respModel.setCode(HttpStatus.OK.value());
		EntityModel<AddressResponseModel> entity = EntityModel.of(respModel);
		entity = addressOrc.generateHateoas(entity, "view-addr", respModel.getAddressId());
		return new ResponseEntity(entity, HttpStatus.OK);	
		
	}	

	
	@CircuitBreaker(name = "addrsBreaker", fallbackMethod = "usersFallbackMethod")
	@GetMapping(value = "/findByCustId/{custId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<AddressResponseModel>> findAddressDetailsByCustID(@PathVariable("custId") String custId) {
			
		AddressResponseModel respModel = new AddressResponseModel();
		List<AddressDetails> detailsLst = addressOrc.fetchAddressDetailsByCustId(custId);
		respModel.setAddressLst(detailsLst);
		respModel.setCode(HttpStatus.OK.value());
		EntityModel<AddressResponseModel> entity = EntityModel.of(respModel);
		entity = addressOrc.generateHateoas(entity, "view-cust-addr", respModel.getAddressId());
		return new ResponseEntity(entity, HttpStatus.OK);	
		
	}	
	

	@CircuitBreaker(name = "addrsBreaker", fallbackMethod = "usersFallbackMethod")
	@DeleteMapping(value = "/{addrId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<AddressResponseModel>> deleteAddressDetails(@PathVariable("addrId") String addrId) {
			
		AddressResponseModel respModel = new AddressResponseModel();
		respModel = addressOrc.deleteDetails(addrId);
		EntityModel<AddressResponseModel> entity = EntityModel.of(respModel);
		entity = addressOrc.generateHateoas(entity, "delete-addr", respModel.getAddressId());
		return new ResponseEntity(entity, HttpStatus.OK);	
		
	}	


	public ResponseEntity<UsersResponseModel> usersFallbackMethod(CallNotPermittedException ex) {
		UsersResponseModel respModel = new UsersResponseModel();
		respModel.setCode(HttpStatus.BAD_GATEWAY.value());
		respModel.setMessage(ex.getMessage());
		return  new ResponseEntity(respModel, HttpStatus.BAD_GATEWAY);
	}
}
