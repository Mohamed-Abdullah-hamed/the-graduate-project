package com.main.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.main.entity.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Long> {

	@EntityGraph(attributePaths = { "roles" ,"cust"})
	@Transactional
	AppUser findByEmail(String email);
}
