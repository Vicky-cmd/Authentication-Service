package com.infotrends.in.InfoTrendsIn.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesGroupRequestModel {

	private String salesGroupId;
	private String salesGroupName;
	private String salesGroupOwner;
	private List<String> salesGroupUsers;
	private String salesGroupActive;
	
}
