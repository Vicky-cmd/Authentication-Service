package com.infotrends.in.InfoTrendsIn.config.security.service;

import com.infotrends.in.InfoTrendsIn.config.security.AppUser;
import com.infotrends.in.InfoTrendsIn.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Override
    public AppUser loadUserByUsername(String s) throws UsernameNotFoundException {
        return new AppUser(usersService.getByUsrName(s).orElseThrow(() -> new UsernameNotFoundException("User Not Found")));
    }
}
