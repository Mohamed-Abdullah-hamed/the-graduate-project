package com.main;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.main.repo.RoleRepo;
import com.main.service.CustService;

@Component
public class AppRunner implements CommandLineRunner {
	@Autowired
	private CustService custService;
	@Autowired
	private RoleRepo roleRepo;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public void run(String... args) throws Exception {
/*
		if (custService.findAll().size() <= 0) {

		}
		*/
	}

}
