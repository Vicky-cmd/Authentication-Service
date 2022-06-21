package com.infotrends.in.authenticationserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationModel {

    private String username;
    private String password;
}
