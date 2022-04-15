package com.romel.online.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romel.online.store.controller.commons.ObjectResponse;
import com.romel.online.store.controller.enums.CRUDEnum;
import com.romel.online.store.controller.generic.GenericController;
import com.romel.online.store.dto.ProductDTO;
import com.romel.online.store.service.ProductService;
import com.romel.online.store.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController extends GenericController{
	
	
	
	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public ResponseEntity<ObjectResponse> findAll(){
		try {

			List<ProductDTO> lst = productService.list();
			if (lst.isEmpty()) {	
				return super.notFound();
			}
			return super.ok(lst, CRUDEnum.CONSULTA);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return super.error(e);

		}
	}
	
	@GetMapping(value = {"name/{name}","name/"})
	public ResponseEntity<ObjectResponse> findByName(@PathVariable(required = false) String name) {
		try {
			if (name==null) {
				return super.ok(productService.list(), CRUDEnum.CONSULTA);
			}
			List<ProductDTO> opt = productService.findByName(name);
			if (opt.isEmpty()) {
				super.ok(null, CRUDEnum.CONSULTA);
			}
			return super.ok(opt, CRUDEnum.CONSULTA);

		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return super.error(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ObjectResponse> findById(@PathVariable Long id) {
		try {
			if (id <= 0) {
				return super.badRequest("Id no válido, debe ser mayor que cero");
			}
			Optional<ProductDTO> opt = productService.findById(id);
			if (opt.isEmpty() || !opt.isPresent()) {
				return super.notFound(id);
			}
			return super.ok(opt.get(), CRUDEnum.CONSULTA);

		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return super.error(e);
		}
	}
	
	@PostMapping
	public ResponseEntity<ObjectResponse> post(@RequestBody @Validated ProductDTO productDTO,	BindingResult result) {

		if (result.hasErrors()) {
			return super.badRequest(result);
		}
		try {
			boolean exist = productService.findByNameRegister(productDTO.getName());
			
			if (exist) {
				return super.badRequest("El producto: '"+ productDTO.getName() + "' ya se encuentra registrado");
			}
			ProductDTO resProductDTO = productService.save(productDTO);
			if (resProductDTO != null) {
				return super.ok(resProductDTO, CRUDEnum.REGISTRO);
			}
			return super.badRequest("Error al registrar el producto");
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return super.customError("Eror al registrar el producto");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ObjectResponse> put(@PathVariable Long id, @RequestBody ProductDTO productDTO,
			BindingResult result) {

		
		if (result.hasErrors()) {
			return super.badRequest(result);
		}

		try {
			boolean exist = productService.findByNameUpdate(productDTO);
			
			if (exist) {
				return super.badRequest("El producto: '"+ productDTO.getName() + "' ya se encuentra registrado");
			}
			Optional<ProductDTO> optProductDTO = productService.findById(id);

			if (!optProductDTO.isEmpty()) {

				ProductDTO oProductoDTO = optProductDTO.get();

				productDTO.setId(id);
				BeanUtils.copyProperties(productDTO, oProductoDTO);

				ProductDTO resProductDTO = productService.save(oProductoDTO);

				if (resProductDTO != null) {
					return super.ok(resProductDTO, CRUDEnum.ACTUALIZACION);
				}
			}
			return null;
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return super.error(e);
		}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ObjectResponse> delete(@PathVariable Long id) {

		try {
			Optional<ProductDTO> optProductDTO = productService.findById(id);

			if (!optProductDTO.isEmpty()) {
				productService.delete(id);
				return super.ok(optProductDTO, CRUDEnum.ELIMINACION);
			}
			return null;
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return super.error(e);
		}
	}
	

}
