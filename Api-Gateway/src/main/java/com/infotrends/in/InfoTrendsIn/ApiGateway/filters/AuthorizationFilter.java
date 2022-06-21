package com.infotrends.in.InfoTrendsIn.ApiGateway.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotrends.in.InfoTrendsIn.exceptions.model.ExceptionResponseModel;
import com.infotrends.in.InfoTrendsIn.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


//@Component
//@RefreshScope
@Slf4j
public class AuthorizationFilter implements GlobalFilter {

	
//	@Value("${spring.gateway.excludedURLs}")
//	private String urlsStrings;
	
	@Autowired
	@Qualifier("excludedUrls")
	List<String> excludedUrls;
	
	public static final String AUTH_FAILED_CODE="ERR_AUTH_FAIL";
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		excludedUrls = Arrays.asList(urlsStrings);
		ServerHttpRequest req = exchange.getRequest();
		log.info("**************************************************************************");
		log.info("URL is - " + req.getURI().getPath());
		if(isSecured.test(req)) {
			try {
				boolean hasAccess = authenticate(req);
				if(!hasAccess) {
					log.info("Token Ivalid!");
					log.info("**************************************************************************");
					return this.onError(exchange, "Authorization header is invalid", HttpStatus. UNAUTHORIZED);
				}
			} catch (ExpiredJwtException e) {
				log.info("Token Expired!");
				log.info("**************************************************************************");
				return this.onError(exchange, "Authorization header has expired", HttpStatus.UNAUTHORIZED);
			} catch (JwtException e) {
				log.info("Token Ivalid!");
				log.info("**************************************************************************");
				return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
			}
		}	
		log.info("**************************************************************************");
		return chain.filter(exchange);
	}
	
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
	    ObjectMapper objMapper = new ObjectMapper();
	    ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
	    try {
	    	response.getHeaders().add("Content-Type", "application/json");
			ExceptionResponseModel data = new ExceptionResponseModel(AUTH_FAILED_CODE, "JWT Error", err, null, new Date());
			byte[] byteData = objMapper.writeValueAsBytes(data);
	        return response.writeWith(Mono.just(byteData).map(t -> dataBufferFactory.wrap(t)));
	        
		} catch (JsonProcessingException  e) {
			e.printStackTrace();
		}
        return response.setComplete();
    }
	
	public Predicate<ServerHttpRequest> isSecured = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

	
	 private boolean authenticate(ServerHttpRequest request) {
	        String token = request.getHeaders().getFirst(SecurityConstants.HEADER);
	        if (token != null) {
	            Claims user = Jwts.parser()
	                    .setSigningKey(SecurityConstants.KEY)
	                    .parseClaimsJws(token)
	                    .getBody();

	            if (user != null) {
	                return true;
	            }else{
	                return  false;
	            }

	        }
	        return false;
	    }
}
