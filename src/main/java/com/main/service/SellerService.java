package com.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Address;
import com.main.entity.AppUser;
import com.main.entity.Role;
import com.main.entity.Seller;
import com.main.projection.SellerProjection;
import com.main.repo.RoleRepo;
import com.main.repo.SellerRepo;

@Service
public class SellerService {

	@Autowired
	private SellerRepo repo;
	@Autowired
	private AddressService addrService;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private AppUserService userService;

	public Seller findById(long id) {
		return repo.findById(id).get();
	}

	public List<Seller> findAll() {
		return repo.findAll();
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	@Transactional
	public Seller insert(Seller seller) {
		Address addr = seller.getAddress();
		Role role = roleRepo.findByName("ROLE_SELLER");
		if (addr != null) {
			addr = addrService.insert(addr);
		}
		AppUser user = userService.insert(seller.getUser());
		seller.setAddress(addr);
		user.addRole(role);
		seller.setUser(user);
		return repo.save(seller);
	}

	@Transactional
	public Seller update(Seller seller) {

		Seller oldSeller = findById(seller.getId());
		oldSeller = updateUserFields(oldSeller, seller);
		return oldSeller;
	}

	public Seller updateSecInfo(Seller seller) {
		return repo.save(seller);
	}

	public SellerProjection findSellerProjById(Long id) {
		return repo.findSellerProjById(id).get();
	}

	@Transactional
	private Seller updateUserFields(Seller oldSeller, Seller newSeller) {
		if (newSeller.getName() != null && !newSeller.getName().isBlank()) {
			oldSeller.setName(newSeller.getName());
		}
		Address addr = newSeller.getAddress();

		if (addr == null)
			return repo.save(newSeller);
		if (addr.getId() == null) {
			addr = addrService.insert(addr);
		} else {
			addr = addrService.update(addr);
		}
		oldSeller.setAddress(addr);
		return repo.save(oldSeller);
	}

	public Seller findByName(String name) {
		return repo.findByName(name);
	}

}
