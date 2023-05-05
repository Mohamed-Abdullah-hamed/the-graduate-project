package com.main.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.main.entity.AppUser;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppUserDetail implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AppUser user;
	public AppUserDetail(AppUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> auths = new HashSet<>();
		user.getRoles().forEach(x->{
			auths.add(new SimpleGrantedAuthority(x.getName()));
		});
		return auths;
	}

	@Override
	public String getPassword() {
		if (user == null) {
			return null;
		}
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
