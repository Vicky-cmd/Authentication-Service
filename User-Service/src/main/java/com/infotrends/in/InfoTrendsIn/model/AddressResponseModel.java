package com.infotrends.in.InfoTrendsIn.model;

import java.util.List;

import com.infotrends.in.InfoTrendsIn.data.AddressDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressResponseModel extends AbstractResponseModel {

	private String addressId;
	
	private AddressDetails address;
	private List<AddressDetails> addressLst;
}
