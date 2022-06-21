package com.infotrends.in.InfoTrendsIn.utils;

public class AppConstants {

	public static String base_uri="http://localhost:8081";
	public static String client_id = "sagaegaedghadhaeehaeh.apps.googleusercontent.com";
	public static String client_secret = "sgagagagagaegeahesjjfsjfjfj";
	public static String exec_token_redirect_uri ="/authenticate/google/requestToken/processResponse"; //"/google/requestUserInfo";
	public static String grant_type = "authorization_code";
	
	public static int bCryptEncoderStrength = 10;
	
	public static String send_mail_toNewRegisteredUsr_QName = "mailNewUser";
	public static String token_deactivation_QName = "expireTokens";
	public static String validateOtpUrl = "http://localhost:8085/api/v1/validateAuthToken";
	public static String createOrderPaymentUrl = "http://localhost:8088/api/v1/payment/createOrder";
	public static String confirmOrderPaymentUrl = "http://localhost:8088/api/v1/payment/confirmPayment";

	
	
	public static String USERID_PREFIX = "C0ID";
	public static String USERID_FORMATTOR_PARAM = "%05d";
	
	public static String ADDRESSID_PREFIX = "ADR";
	public static String ADDRESSID_FORMATTOR_PARAM = "%05d";
}
