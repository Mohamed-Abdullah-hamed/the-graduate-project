package com.main.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.entity.Seller;
import com.main.projection.SellerProjection;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Long> {

	@Override
	@EntityGraph(attributePaths = { "user", "cart.items" })
	List<Seller> findAll();

	@Transactional
	@Query(value = "select s.id,s.name,s.user from Seller s join AppUser p on s.user.id=p.id where s.id =:id")
	@EntityGraph(attributePaths = { "user" })
	Optional<SellerProjection> findSellerProjById(Long id);

	@Override
	@Transactional
	@EntityGraph(attributePaths = { "user", "cart.items", "cart", "products", "address", "ratings" })
	Optional<Seller> findById(Long id);

	Seller findByName(String name);
}
