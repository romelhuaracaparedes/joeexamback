package com.romel.online.store.service;

import java.util.List;

import com.romel.online.store.model.Category;
import com.romel.online.store.service.exception.ServiceException;

public interface CategoryService {
	
	List<Category> list() throws ServiceException;

}
