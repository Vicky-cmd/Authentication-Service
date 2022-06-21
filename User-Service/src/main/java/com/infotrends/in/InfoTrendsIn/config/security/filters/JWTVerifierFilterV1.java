package com.infotrends.in.InfoTrendsIn.config.security.filters;

import com.infotrends.in.InfoTrendsIn.utils.Utilities;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JWTVerifierFilterV1 extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(!Utilities.validString(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String header=headerNames.nextElement();
            logger.info(String.format("Header: %s --- Value: %s", header, httpServletRequest.getHeader(header)));

        }
        String token = authHeader.replace("Bearer ", "");
//        Jws<Claims> authClaims = Jwts.parser().setSigningKey(SecurityConstants.KEY).parseClaimsJws(token);
//        String username = authClaims.getBody().getSubject();
//        List<Map<String, String>> authorities = (List<Map<String, String>>) authClaims.getBody().get("authorities");
//        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(a-> new SimpleGrantedAuthority(a.get("authority"))).collect(Collectors.toSet());

        String username=httpServletRequest.getHeader("username");
        List<Map<String, String>> authorities = new ArrayList<>();
        String authoritiesStr = httpServletRequest.getHeader("authorities");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if(Utilities.validString(authoritiesStr)) {
            simpleGrantedAuthorities=Arrays.stream(authoritiesStr.split(",")).distinct()
                    .filter(Utilities::validString).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
