package com.romel.online.store;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.romel.online.store.model.Category;
import com.romel.online.store.model.Product;
import com.romel.online.store.repository.ProductRepository;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductRepositoryTest {
	
	
	@Autowired
	private ProductRepository repository;

	private Product savedEntity;
	
	@BeforeEach
	void setUp(){
		repository.deleteAll();
		Category category = new Category();
		category.setId(1L);
		category.setCode(1);
		category.setName("REPRODUCTOR MULTIMEDIA");
		Product entity = new Product(1L,"redmi","qweasd",1245.0,category,"primeradescripcion");
		savedEntity = repository.save(entity);
		assertEqualsProduct(entity, savedEntity);
	}
	
	@Test
	void create() {
		Category category = new Category();
		category.setId(1L);
		category.setCode(1);
		category.setName("REPRODUCTOR MULTIMEDIA");
		Product newEntity = repository.save(new Product(2L,"Xiomy","zxcasd",1267.0,category,"segundadescipcion"));

	    Product foundEntity = repository.findById(newEntity.getId()).get();
	    assertEqualsProduct(newEntity, foundEntity);

	    assertEquals(2, repository.count());
	}
	
	@Test
	void update() {
	    savedEntity.setName("redmi2");
	    repository.save(savedEntity);

	    Product foundEntity = repository.findById(savedEntity.getId()).get();
	    assertEquals("redmi2", foundEntity.getName());
	}
	
	
	@Test
	void delete() {
	    repository.deleteById(savedEntity.getId());
	    assertFalse(repository.existsById(savedEntity.getId()));
	}
	
	@Test
	void getByProductId() {
	    Optional<Product> entity = repository.findById(savedEntity.getId());

	    assertTrue(entity.isPresent());
	    assertEqualsProduct(savedEntity, entity.get());
	}
	
	
	@Test
	void getByNameCreate() {
	    assertEquals(1, repository.countByNameIgnoreCase(savedEntity.getName()));
	}
	
	@Test
	void getByNameUpdate() {
	    assertEquals(0, repository.findNameUpdate(savedEntity.getName(), savedEntity.getId()));
	}
	
	
	
	private void assertEqualsProduct(Product expectedEntity, Product actualEntity) {
	    assertEquals(expectedEntity.getName(),          actualEntity.getName());
	    assertEquals(expectedEntity.getCode(),        actualEntity.getCode());
	    assertEquals(expectedEntity.getPrice(),           actualEntity.getPrice());
	    assertEquals(expectedEntity.getCategory(),           actualEntity.getCategory());
	    assertEquals(expectedEntity.getDescription(),           actualEntity.getDescription());
	}
	
	
	

}
