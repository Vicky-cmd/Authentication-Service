package com.infotrends.in.InfoTrendsIn.ApiGateway.service;

import com.infotrends.in.InfoTrendsIn.ApiGateway.dao.AuthenticationServiceRepository;
import com.infotrends.in.InfoTrendsIn.ApiGateway.model.ConnValidationResponse;
import com.infotrends.in.InfoTrendsIn.ApiGateway.service.proxy.AuthenticationServiceProxy;
import com.infotrends.in.InfoTrendsIn.ApiGateway.utils.Utilities;
import com.infotrends.in.InfoTrendsIn.exceptions.BusinessException;
import com.infotrends.in.InfoTrendsIn.exceptions.CustomRuntimeException;
import com.infotrends.in.InfoTrendsIn.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenService {

//    @Autowired
//    private AuthenticationServiceProxy authenticationServiceProxy;

    @Autowired
    private AuthenticationServiceRepository authenticationServiceRepository;


    public ConnValidationResponse validateAuthenticationToken(String bearerToken) {

        if(Utilities.isTokenValid(bearerToken)) {
            String token = bearerToken.replace(SecurityConstants.PREFIX, "");

            try {
//                ResponseEntity<ConnValidationResponse> responseEntity = authenticationServiceProxy.validateConnection(token);
//                if(responseEntity.getStatusCode().is2xxSuccessful()) {
//                    return responseEntity.getBody();
//                } else if(responseEntity.getStatusCode().is4xxClientError()) {
//                    int errorCode = responseEntity.getStatusCode().value();
//                    if(errorCode==401) {
//                        throw new BusinessException("", HttpStatus.UNAUTHORIZED, "Unauthorized");
//                    } else if(errorCode==403) {
//                        throw new BusinessException("", HttpStatus.FORBIDDEN, "Forbidden");
//                    }
//                }

                return authenticationServiceRepository.validateConnection(bearerToken);

            } catch (Exception e) {
                throw new BusinessException("", HttpStatus.UNAUTHORIZED, "Unauthorized");

            }
        }

        throw new BusinessException("", HttpStatus.UNAUTHORIZED, "Unauthorized");

    }

}
