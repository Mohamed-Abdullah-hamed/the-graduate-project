package com.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Address;
import com.main.entity.AppUser;
import com.main.entity.Customer;
import com.main.entity.Role;
import com.main.entity.Seller;
import com.main.repo.CustRepo;
import com.main.repo.RoleRepo;

@Service
public class CustService {

	@Autowired
	private CustRepo repo;
	@Autowired
	private AddressService addrService;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private AppUserService userService;
	@Autowired
	private SellerService sellerService;

	public Customer findById(long id) {
		return repo.findById(id).get();
	}

	public List<Customer> findAll() {
		return repo.findAll();
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	@Transactional
	public Customer insert(Customer cust) {
		String roleName = cust.getUserType().equals("Customer") ? "ROLE_USER" : "ROLE_SELLER";
		
		if(roleName.equals("ROLE_SELLER")) {
			Seller seller = new Seller();
			seller.setAddress(cust.getAddress());
			seller.setName(cust.getName());
			seller.setUser(cust.getUser());
			return sellerService.insert(seller);
		} 
		Address addr = cust.getAddress();
		Role role = roleRepo.findByName(roleName);
		if (addr != null) {
			addr = addrService.insert(addr);
		}
		AppUser user = userService.insert(cust.getUser());
		cust.setAddress(addr);
		user.addRole(role);
		cust.setUser(user);
		return repo.save(cust);
	}

	@Transactional
	public Customer update(Customer cust) {
		System.err.println(cust);
		System.err.println(cust.getAddress());
		Customer oldCust = findById(cust.getId());
		oldCust = updateUserFields(oldCust, cust);	
		return oldCust;
	}

	public Customer updateSecInfo(Customer cust) {
		return repo.save(cust);
	}

	@Transactional
	private Customer updateUserFields(Customer oldCust, Customer newCust) {
		if (newCust.getName() != null && !newCust.getName().isBlank()) {
			oldCust.setName(newCust.getName());
		}
		Address addr = newCust.getAddress();

		if (addr == null)
			return repo.save(newCust);
		if (addr.getId() == null) {
			addr = addrService.insert(addr);
		} else {
			addr = addrService.update(addr);
		}
		oldCust.setAddress(addr);
		return repo.save(oldCust);
	}

	public Customer findByName(String name) {
		return repo.findByName(name);
	}

	

}
