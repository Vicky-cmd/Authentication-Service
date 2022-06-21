package com.infotrends.in.InfoTrendsIn.config.security;

import com.infotrends.in.InfoTrendsIn.data.Users;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@ToString
public class AppUser implements UserDetails {

    private final Users user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        log.info(user.toString());
        if(user.getIsAdmin().equalsIgnoreCase("Y"))
            return AppRoles.ADMIN.getAuthorities();
        else
            return AppRoles.USER.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getDeletedFlag().equalsIgnoreCase("Y");
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getDeletedFlag().equalsIgnoreCase("Y");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.getDeletedFlag().equalsIgnoreCase("Y");
    }

    @Override
    public boolean isEnabled() {
        return !user.getDeletedFlag().equalsIgnoreCase("Y");
    }
}
