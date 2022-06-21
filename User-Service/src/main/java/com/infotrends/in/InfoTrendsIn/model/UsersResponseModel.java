package com.infotrends.in.InfoTrendsIn.model;

import java.util.List;

import com.infotrends.in.InfoTrendsIn.data.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersResponseModel extends AbstractResponseModel {

	private String userId;
	
	private Users user;
	private List<Users> usersLst;
}
