package com.infotrends.in.authenticationserver.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {

    GUEST(new HashSet<>(Arrays.asList(UserPermission.READ_ONLY))),
    USER(new HashSet<>(Arrays.asList(UserPermission.USER, UserPermission.WRITE_ONLY))),
    MODERATOR(new HashSet<>(Arrays.asList(UserPermission.READ_ONLY, UserPermission.WRITE_ONLY, UserPermission.READ_ONLY_ADMIN))),
    ADMIN(new HashSet<>(Arrays.asList(UserPermission.ADMIN)));

    Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions=permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return this.permissions.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toSet());
    }

}
