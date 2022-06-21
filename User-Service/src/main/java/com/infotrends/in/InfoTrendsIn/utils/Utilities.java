package com.infotrends.in.InfoTrendsIn.utils;

import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.infotrends.in.InfoTrendsIn.model.UsersResponseModel;

public class Utilities {

	
	public static String generateId(String prefix_param, String format_param, int sequence) {
		return prefix_param + String.format(format_param, sequence);
	}
	
	public static EntityModel<?> addLinksToEntity(EntityModel<?> entityModel, Map<String, WebMvcLinkBuilder> buildersMap) {
		
		buildersMap.forEach((key, builder) -> {
			entityModel.add(builder.withRel(key));
		});
		return entityModel;
	}
	
	public static boolean validString(String input) {
		return input!=null && !input.isEmpty();
	}
	

	public static boolean validLong(long input) {
		return input>0;
	}
}
