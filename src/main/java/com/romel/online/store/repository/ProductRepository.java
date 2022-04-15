package com.romel.online.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.romel.online.store.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByNameContainingIgnoreCase(@Param("name") String name);
	
	int countByNameIgnoreCase(String name);
	
	@Query("select count(p) from Product p where upper(p.name) = upper(:name) and p.id != :id")	//JPQL
	int findNameUpdate(@Param("name") String name, @Param("id") Long id);
}
