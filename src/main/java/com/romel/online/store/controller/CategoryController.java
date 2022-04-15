package com.romel.online.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romel.online.store.controller.commons.ObjectResponse;
import com.romel.online.store.controller.enums.CRUDEnum;
import com.romel.online.store.controller.generic.GenericController;
import com.romel.online.store.model.Category;
import com.romel.online.store.service.CategoryService;
import com.romel.online.store.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController  extends GenericController{

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/list")
	public ResponseEntity<ObjectResponse> listAll(){
		try {
			List<Category> lstCategory = categoryService.list();
			if (lstCategory.isEmpty()) {
				return super.notFound();
			}
			return super.ok(lstCategory, CRUDEnum.CONSULTA);
			
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return super.error(e);
		}
		
		
	}

}
