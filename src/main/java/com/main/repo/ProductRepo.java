package com.main.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{

	List<Product> findByCategory(String category);
	@Query("select p from Product p where p.name like %:name%")
	@EntityGraph(attributePaths = {"seller","items","ratings"})
	List<Product> findByName(@Param("name") String name);
	@Query("select p.name from Product p where p.name like %:name%")
	List<String> findProductNames(@Param("name") String name);
	@Override
	@Transactional
	@EntityGraph(attributePaths = {"seller","items","ratings"})
	List<Product> findAll();
	
	@Override
	@Transactional
	@EntityGraph(attributePaths = {"seller","items","ratings"})
	Optional<Product> findById(Long id);
	
}
