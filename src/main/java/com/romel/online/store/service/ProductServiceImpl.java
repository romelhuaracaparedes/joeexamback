package com.romel.online.store.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romel.online.store.dto.ProductDTO;
import com.romel.online.store.model.Product;
import com.romel.online.store.repository.ProductRepository;
import com.romel.online.store.service.exception.ServiceException;


@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public List<ProductDTO> list() throws ServiceException {
		try {
			return productRepository.findAll().stream()
					.map(e-> this.getProductDTO(e))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<ProductDTO> findById(Long id) throws ServiceException {
		try {
			Optional<Product> optProduct=  productRepository.findById(id);
			if (!optProduct.isEmpty() && optProduct.isPresent()) {
				return Optional.of(this.getProductDTO(optProduct.get()));
			}
			return Optional.empty();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ProductDTO save(ProductDTO productDTO) throws ServiceException {
		try {
			
			Product product = this.getProductEntity(productDTO);
			
			Product retProduct = productRepository.save(product);
				
			return this.getProductDTO(retProduct);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	

	@Override
	public List<ProductDTO> findByName(String name) throws ServiceException {
		try {
			List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
			return products.stream()
					.map(e-> this.getProductDTO(e))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void delete(Long id) throws ServiceException {
		try {
			productRepository.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean findByNameRegister(String name) throws ServiceException {
		try {
			int count=  productRepository.countByNameIgnoreCase(name);
			if (count>0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean findByNameUpdate(ProductDTO productDTO) throws ServiceException {
		try {
			int count=  productRepository.findNameUpdate(productDTO.getName(), productDTO.getId());
			if (count>0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	
	
	private ProductDTO getProductDTO(Product product) {
		return objectMapper.convertValue(product, ProductDTO.class);
	}
	
	private Product getProductEntity(ProductDTO productDTO) {
		return objectMapper.convertValue(productDTO, Product.class);
	}

	

	
}
