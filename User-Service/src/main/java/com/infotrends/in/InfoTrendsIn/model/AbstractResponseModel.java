package com.infotrends.in.InfoTrendsIn.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractResponseModel {

	private int code;
	private String message;
	
}
