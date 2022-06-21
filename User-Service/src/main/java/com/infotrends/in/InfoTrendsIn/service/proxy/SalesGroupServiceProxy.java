package com.infotrends.in.InfoTrendsIn.service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.infotrends.in.InfoTrendsIn.exceptions.validations.ValidationLevel;
import com.infotrends.in.InfoTrendsIn.model.SalesGroupRequestModel;
import com.infotrends.in.InfoTrendsIn.model.SalesGroupResponseModel;


@FeignClient(name = "sales-group-service")
public interface SalesGroupServiceProxy {

	@GetMapping(value = "/api/v1/salesGroup/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public SalesGroupResponseModel getGroupById(@PathVariable("id") String id);
	
	@PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EntityModel<SalesGroupResponseModel>> createGroup(@Validated(ValidationLevel.onCreate.class) @RequestBody SalesGroupRequestModel salesGpRequest) ;
}
