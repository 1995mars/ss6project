package com.tttmars.ss6project.services;

import com.tttmars.ss6project.model.Roles;
import com.tttmars.ss6project.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    private PasswordEncoder passwordEncoder;

    public CustomUserDetails(Users user) {
        this.username = user.getUsername();
        this.password= user.getPassword();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // Add authorities from roles
        grantedAuthorities.addAll(user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet()));

        // Add authorities from user authorities
        grantedAuthorities.addAll(user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getApiPattern()))
                .collect(Collectors.toSet()));
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}