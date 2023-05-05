package com.main.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepo extends JpaRepository<ShoppingCart,Long>{

	@EntityGraph(attributePaths = {"items"})
	public Optional<ShoppingCart> findById(Long id);
}
