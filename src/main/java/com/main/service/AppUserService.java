package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.entity.AppUser;
import com.main.repo.UserRepo;
import com.main.security.AppUserDetail;

@Service
public class AppUserService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	public AppUser findById(long id) {
		return repo.findById(id).get();
	}

	public List<AppUser> findAll() {
		return repo.findAll();
	}

	public AppUser insert(AppUser user) {
		return repo.save(user);
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	public AppUser update(AppUser user) {
		return repo.save(user);
	}
	public AppUser findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser user = findByEmail(email);
		AppUserDetail userDetail = new AppUserDetail(user);
		return userDetail;
	}

}
