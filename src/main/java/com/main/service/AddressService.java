package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Address;
import com.main.repo.AddressRepo;

@Service
public class AddressService {

	@Autowired
	private AddressRepo repo;
	
	public Address findById(long id) {
		return repo.findById(id).get();
	}

	public List<Address> findAll() {
		return repo.findAll();
	}

	public Address insert(Address addr) {
		return repo.save(addr);
	}
	
	public void deleteById(long id) {
		repo.deleteById(id);
	}

	public Address update(Address addr) {
		return repo.save(addr);
	}

}
