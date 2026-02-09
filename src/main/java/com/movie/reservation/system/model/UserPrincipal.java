package com.movie.reservation.system.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private Users user;
    
    public UserPrincipal(Users user) {
        this.user = user;
    }

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

    @Override
	public String getUsername() {
		return user.getUser_id().toString();
	}
}
