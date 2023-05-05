package com.main.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.entity.Customer;
import com.main.entity.Seller;

@Repository
public interface CustRepo extends JpaRepository<Customer,Long>{

	@Transactional
	@Override
	@EntityGraph(attributePaths = {"user","cart.items","cart","address"})
	List<Customer> findAll();
	
	@Transactional
	@Override
	@EntityGraph(attributePaths = {"user","cart.items","cart","address"})
	Optional<Customer> findById(Long id);

	Customer findByName(String name);
	
	
	
}
