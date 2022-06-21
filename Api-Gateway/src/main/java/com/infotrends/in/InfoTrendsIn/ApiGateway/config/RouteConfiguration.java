package com.infotrends.in.InfoTrendsIn.ApiGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.infotrends.in.InfoTrendsIn.ApiGateway.filters.AuthorizationFilter;

//@Configuration
//@RefreshScope
//public class RouteConfiguration {
//
////	@Autowired
////    AuthorizationFilter authorizationFilter;
//
//
//	@Bean
//	public AuthorizationFilter authorizationFilter() {
//		return new AuthorizationFilter();
//	}
//
//
////    @Bean
////    public RouteLocator apiRoutes(RouteLocatorBuilder builder) {
////    	return builder.routes()
////    			.route("001", p -> p.path("/*-service/**")
////    								.filters(f -> f.filter(authorizationFilter).stripPrefix(1))
////    								.uri("http://localhost:8765"))
////    			.build();
////    }
//}
