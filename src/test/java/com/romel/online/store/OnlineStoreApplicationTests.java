package com.romel.online.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romel.online.store.dto.ProductDTO;
import com.romel.online.store.model.Category;
import com.romel.online.store.model.Product;
import com.romel.online.store.repository.ProductRepository;
import com.romel.online.store.service.ProductServiceImpl;
import com.romel.online.store.service.exception.ServiceException;

@SpringBootTest
class OnlineStoreApplicationTests {

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Mock
	private ObjectMapper objectMapper;
	
	
	
	@Test
	void list() throws ServiceException{
		
		List<Product> lst= new ArrayList<Product>();
		lst.add(new Product());
		
		when(productRepository.findAll()).thenReturn(lst);	
		
		List<ProductDTO> lstResult = productService.list();
		
		assertEquals(lst.stream()
				.map(e-> this.getProductDTO(e))
				.collect(Collectors.toList()), lstResult);
		verify(productRepository).findAll();
		
	}
	
	
	@Test
	void findById() throws ServiceException{
		Optional<Object> entitySave = Optional.empty();
		when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
		Optional<ProductDTO> result = productService.findById(5L);
		verify(productRepository,times(1)).findById(5L);
		assertEquals(entitySave, result);
		
	}
	
	@Test
	void save() throws ServiceException{
		Category category = new Category();
		category.setId(2L);
		category.setCode(1);
		category.setName("REPRODUCTOR MULTIMEDIA");
		Product product = new Product(5L,"POCO","ertreytry",5245.0,category,"desssssscripcion");

        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);

        ProductDTO created = productService.save(this.getProductDTO(product));
        
        assertEquals(this.getProductDTO(product), created);
		
	}
	
	
	@Test
	void findByName() throws ServiceException{
		
		List<Product> lst= new ArrayList<Product>();
		lst.add(new Product());
		
		when(productRepository.findByNameContainingIgnoreCase(ArgumentMatchers.anyString())).thenReturn(lst);	
		
		List<ProductDTO> lstResult = productService.findByName("");
		
		assertEquals(lst.stream()
				.map(e-> this.getProductDTO(e))
				.collect(Collectors.toList()), lstResult);
		verify(productRepository).findByNameContainingIgnoreCase("");
		
	}
	
	
	
	@Test
	void delete() throws ServiceException{
		
		
		doNothing().when(productRepository).deleteById(ArgumentMatchers.anyLong());
		productService.delete(ArgumentMatchers.anyLong());
		verify(productRepository).deleteById(ArgumentMatchers.anyLong());
		
	}
	
	@Test
	void findByNameRegister() throws ServiceException{
		
		
		int lst=1;
		int count=0;
		when(productRepository.countByNameIgnoreCase(ArgumentMatchers.anyString())).thenReturn(lst);	
		
		boolean result = productService.findByNameRegister("");
		if (result) {
			count=1;
		}
		
		assertEquals(1, count);
		verify(productRepository).countByNameIgnoreCase("");
		
	}
	
	@Test
	void findByNameUpdate() throws ServiceException{
		int lst=1;
		int count=0;
		ProductDTO productDTO = new ProductDTO(5L,"POCO","ertreytry",5245.0,null,"desssssscripcion");
		
		when(productRepository.findNameUpdate(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong())).thenReturn(lst);	
		
		boolean result = productService.findByNameUpdate(productDTO);
		if (result) {
			count=1;
		}
		
		assertEquals(1, count);
		verify(productRepository).findNameUpdate(productDTO.getName(),productDTO.getId());
		
	}
	
	
	
	
	
	private ProductDTO getProductDTO(Product product) {
		return objectMapper.convertValue(product, ProductDTO.class);
	}
	
	private Product getProductEntity(ProductDTO productDTO) {
		return objectMapper.convertValue(productDTO, Product.class);
	}

}
