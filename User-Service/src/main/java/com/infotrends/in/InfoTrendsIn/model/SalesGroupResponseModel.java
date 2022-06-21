package com.infotrends.in.InfoTrendsIn.model;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalesGroupResponseModel extends AbstractResponseModel{

	private String salesGroupId;
	private SalesGroup salesGroup;
	private List<SalesGroup> salesGroupLst;
	private String errorCode;
	private Map<String, String> errors;
	
}
