package com.infotrends.in.InfoTrendsIn.ApiGateway.utils;

import com.infotrends.in.InfoTrendsIn.security.SecurityConstants;

public class Utilities {

	
	public static String generateId(String prefix_param, String format_param, int sequence) {
		return prefix_param + String.format(format_param, sequence);
	}
	
	public static boolean validString(String input) {
		return input!=null && !input.isEmpty();
	}
	

	public static boolean validLong(long input) {
		return input>0;
	}

	public static boolean isTokenValid(String bearerToken) {
		return validString(bearerToken) && bearerToken.startsWith(SecurityConstants.PREFIX);
	}
}
