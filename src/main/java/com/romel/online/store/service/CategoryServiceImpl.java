package com.romel.online.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romel.online.store.model.Category;
import com.romel.online.store.repository.CategoryRepository;
import com.romel.online.store.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> list() throws ServiceException {
		
		try {
			List<Category> listCategory = categoryRepository.findAll();
			return listCategory;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

}
