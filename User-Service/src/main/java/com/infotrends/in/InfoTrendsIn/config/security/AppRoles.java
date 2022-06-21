package com.infotrends.in.InfoTrendsIn.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public enum AppRoles {

    READONLY_USER(new HashSet<>(Arrays.asList(AppPermissions.USER_READ, AppPermissions.ADDRESS_READ))),
    USER(new HashSet<>(Arrays.asList(AppPermissions.USER_READ, AppPermissions.USER_WRITE, AppPermissions.ADDRESS_READ, AppPermissions.ADDRESS_WRITE))),
    ADMIN(new HashSet<>(Arrays.asList(AppPermissions.ADDRESS_READ))),
    SUPER_ADMIN(new HashSet<>(Arrays.asList(AppPermissions.USER_READ, AppPermissions.USER_WRITE, AppPermissions.ADDRESS_READ, AppPermissions.ADDRESS_WRITE)));

    Set<AppPermissions> permissions = new HashSet<>();

    AppRoles(Set<AppPermissions> permissions) {
        this.permissions=permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        log.info("Granted Authority for" + this.name() + " - " + this.permissions.stream().map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toSet()).toString());
        return this.permissions.stream().map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toSet());
    }
}
