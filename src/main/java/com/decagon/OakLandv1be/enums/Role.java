package com.decagon.OakLandv1be.enums;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum Role {
    SUPERADMIN,
    ADMIN,
    CUSTOMER;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        java.util.Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
