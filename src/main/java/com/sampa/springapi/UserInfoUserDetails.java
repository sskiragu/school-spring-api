package com.sampa.springapi;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sampa.springapi.model.User;

public class UserInfoUserDetails implements UserDetails {
	
	private Long id;
	private String username;
	private String password;
	private List<GrantedAuthority> grantedAuthorities;

	public UserInfoUserDetails(User user) {
		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
		grantedAuthorities = user.getUserRoles().stream()
				.map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName())).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
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

	public Long getId() {
		return id;
	}

}
