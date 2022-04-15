package com.romel.online.store.service;

import java.util.List;
import java.util.Optional;

import com.romel.online.store.dto.ProductDTO;
import com.romel.online.store.service.exception.ServiceException;

public interface ProductService {
	List<ProductDTO> list() throws ServiceException;
	Optional<ProductDTO> findById(Long id) throws ServiceException;
	ProductDTO save(ProductDTO productDTO) throws ServiceException;
	List<ProductDTO> findByName(String name) throws ServiceException;
	void delete(Long id) throws ServiceException;
	boolean findByNameRegister(String name) throws ServiceException;
	boolean findByNameUpdate(ProductDTO productDTO) throws ServiceException;
}
