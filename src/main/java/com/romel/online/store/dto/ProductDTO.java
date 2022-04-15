package com.romel.online.store.dto;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.romel.online.store.model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	
	
	private Long id;
	
	@Size(min = 5, message = "{name-message}")
	private String name;
	
	@Size(min = 5, message = "{code-message}")
	private String code;
	
	@Positive(message = "{price-message}")
	private Double price;
	
	private Category category;
	
	@Size(min = 5, max = 60, message = "{description-message}")
	private String description;
}
